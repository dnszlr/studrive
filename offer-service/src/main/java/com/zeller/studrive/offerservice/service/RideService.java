package com.zeller.studrive.offerservice.service;

import com.zeller.studrive.offerservice.basic.Constant;
import com.zeller.studrive.offerservice.basic.MapboxClient;
import com.zeller.studrive.offerservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.offerservice.model.Address;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.model.RideStatus;
import com.zeller.studrive.offerservice.repository.RideRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.geo.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RideService {

	private final RideRepository rideRepository;
	private final TaskSender taskSender;
	private final MapboxClient mapboxClient;

	public RideService(RideRepository rideRepository, TaskSender taskSender, MapboxClient mapboxClient) {
		this.rideRepository = rideRepository;
		this.taskSender = taskSender;
		this.mapboxClient = mapboxClient;
	}

	final Logger logger = LoggerFactory.getLogger(RideService.class);

	/**
	 * Checks if the passed ride is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param ride - the ride to be saved
	 * @return the newly created ride
	 */
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public Ride save(Ride ride) {
		return rideRepository.save(ride);
	}

	/**
	 * Offers a new ride to the public
	 *
	 * @param ride - the ride to be offered
	 * @return the newly created ride or null
	 */
	public Optional<Ride> offerRide(Ride ride) {
		Optional<Ride> rideTemp = Optional.empty();
		if(mapboxClient.getGeodata(ride.getStart()) &&
				mapboxClient.getGeodata(ride.getDestination())) {
			ride.setRideStatus(RideStatus.AVAILABLE);
			mapboxClient.getGeodata(ride.getStart());
			mapboxClient.getGeodata(ride.getDestination());
			rideTemp = Optional.of(this.save(ride));
		}
		return rideTemp;
	}

	/**
	 * Returns the ride matching the passed id
	 *
	 * @param rideId - The id of the requested ride
	 * @return The ride or null
	 */
	public Optional<Ride> findById(String rideId) {
		return rideRepository.findRidesById(rideId);
	}

	/**
	 * Cancels the ride that belongs to the passed id by changing its status to canceled
	 *
	 * @param id - the id of the trip to be cancelled
	 * @return the canceled ride or null
	 */
	public Optional<Ride> cancelRide(String id) {
		Optional<Ride> rideTemp = this.rideRepository.findRidesById(id);
		if(rideTemp.isPresent()) {
			Ride ride = rideTemp.get();
			if(checkRideStatus(ride, RideStatus.AVAILABLE) || checkRideStatus(ride, RideStatus.OCCUPIED)) {
				ride.setRideStatus(RideStatus.CANCELED);
				try {
					this.save(ride);
					logger.info("RideService.cancelRide: Ride with the id " + ride.getId() + " got " + ride.getRideStatus());
					taskSender.cancelRide(ride.getId());
				} catch(Exception ex) {
					rideTemp = Optional.empty();
					logger.info("RideService.cancelRide: Problems canceling the Ride with the id " + ride.getId(), ex);
				}
			}
		}
		return rideTemp;
	}

	/**
	 * Closes the ride that belongs to the passed id by changing its status to close
	 * This can only happen if the ride is at least 24 hours old and has not been already canceled or closed
	 *
	 * @param id - the id of the trip to be closed
	 * @return the closed ride or null
	 */
	public Optional<Ride> closeRide(String id) {
		Optional<Ride> rideTemp = this.rideRepository.findRidesById(id);
		if(rideTemp.isPresent()) {
			Ride ride = rideTemp.get();
			if(validateTime(ride.getEndDate()) &&
					(checkRideStatus(ride, RideStatus.AVAILABLE) || checkRideStatus(ride, RideStatus.OCCUPIED))) {
				ride.setRideStatus(RideStatus.CLOSED);
				try {
					this.save(ride);
					logger.info("RideService.closeRide: Ride with the id " + ride.getId() + " got " + ride.getRideStatus());
					taskSender.closeRide(ride.getId());
				} catch(Exception ex) {
					rideTemp = Optional.empty();
					logger.info("RideService.closeRide: Problems closing the Ride with the id " + ride.getId(), ex);
				}
			}
		}
		return rideTemp;
	}

	/**
	 * Searches all rides of a driver
	 *
	 * @param driverId - The id of the driver whose trips should be returned
	 * @return The list of rides for the passed driver
	 */
	public List<Ride> getRidesByDriver(Long driverId) {
		return rideRepository.findRidesByDriverId(driverId);
	}

	/**
	 * Check if there are still free seats in the car.
	 *
	 * @param rideId - The id of the car whose status is to be checked
	 * @return true if there are available seats, false if not
	 */
	public boolean verifyRideSeats(String rideId) {
		Optional<Ride> rideTemp = rideRepository.findRidesById(rideId);
		return rideTemp.isPresent() && checkRideStatus(rideTemp.get(), RideStatus.AVAILABLE);
	}

	/**
	 * Initializes a geoquery for the passed parameters and then merges the results
	 *
	 * @param startDate   - The day the journey should begin
	 * @param start       - The address where the ride starts
	 * @param destination - The destination where the ride ends
	 * @return The list of available rides for the passed values
	 */
	public List<Ride> getAvailableRide(LocalDate startDate, Address start, Address destination) {
		LocalDateTime ldt = LocalDateTime.of(startDate, LocalTime.of(0, 0, 0));
		// It is not possible to perform the query for both addresses at the same time, so one must be performed first and then the other.
		// Therefore, the results must be combined afterwards.
		List<Ride> startResult = getAvailableRidesList(Constant.STARTINDEX, ldt, start);
		List<Ride> destinationResult = getAvailableRidesList(Constant.DESTINATIONINDEX, ldt, destination);
		startResult.retainAll(destinationResult);
		logger.info(startResult.size() + " available rides were found");
		return startResult;
	}

	/**
	 * Calls the repository to perform a geoquery to find all available rides.
	 *
	 * @param index     - The index that must be applied to the respective address field
	 * @param formatted - The LocalDateTime formatted from a simple LocalDate
	 * @param address   - The address for which the geoquery should be performed
	 * @return The list of available rides for the passed values
	 */
	private List<Ride> getAvailableRidesList(String index, LocalDateTime formatted, Address address) {
		List<GeoResult<Ride>> result = Collections.emptyList();
		if(mapboxClient.getGeodata(address)) {
			double[] coords = address.getCoordinates();
			Point point = new Point(coords[0], coords[1]);
			result = rideRepository.findAvailableRides(index, formatted, point).getContent();

		}
		return result.stream().map(GeoResult::getContent).collect(Collectors.toList());
	}

	/**
	 * Checks if the passed time plus one day is greater than or equal to the current time
	 *
	 * @param time - The time to be checked
	 * @return true if the passed time plus days is greater than or equal to the current time, false if not
	 */
	private boolean validateTime(LocalDateTime time) {
		return time.plusDays(1).isAfter(LocalDateTime.now());
	}

	/**
	 * Checks if the passed ride has the passed rideStatus
	 *
	 * @param ride       - The ride to be checked
	 * @param rideStatus - The rideStatus searched for
	 * @return true if the passed ride has the rideStatus, false if not
	 */
	private boolean checkRideStatus(Ride ride, RideStatus rideStatus) {
		return ride.getRideStatus() == rideStatus;
	}
}

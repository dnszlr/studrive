package com.zeller.studrive.offerservice.service;

import com.zeller.studrive.offerservice.helper.Constants;
import com.zeller.studrive.offerservice.helper.MapboxGeocoding;
import com.zeller.studrive.offerservice.model.Address;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.model.RideStatus;
import com.zeller.studrive.offerservice.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RideService {

	@Autowired
	private RideRepository rideRepository;
	@Autowired
	private MapboxGeocoding mapboxGeocoding;

	/**
	 * Checks if the passed ride is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param ride - the ride to be saved
	 * @return the newly created ride
	 */
	public Ride offerRide(Ride ride) {
		// TODO Validation der übergebenen Werte
		ride.setRideStatus(RideStatus.AVAILABLE);
		mapboxGeocoding.getGeodata(ride.getStart());
		mapboxGeocoding.getGeodata(ride.getDestination());
		return this.rideRepository.save(ride);
	}

	/**
	 * Cancels the ride that belongs to the passed id by changing its status to canceled
	 *
	 * @param id - the id of the trip to be cancelled
	 * @return the canceled ride or null
	 */
	public Optional<Ride> cancelRide(String id) {
		Optional<Ride> rideTemp = this.rideRepository.findById(id);
		if(rideTemp.isPresent()) {
			Ride ride = rideTemp.get();
			ride.setRideStatus(RideStatus.CANCELED);
			// TODO Async Sitzplätze aktualisieren
			// TODO Benachrichtungen an Mitfahrer?
			this.rideRepository.save(ride);
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
		Optional<Ride> rideTemp = this.rideRepository.findById(id);
		if(rideTemp.isPresent()) {
			Ride ride = rideTemp.get();
			if(validateTime(ride.getEndDate(), 1) &&
					(checkRideStatus(ride, RideStatus.AVAILABLE) || checkRideStatus(ride, RideStatus.OCCUPIED))) {
				ride.setRideStatus(RideStatus.CLOSED);
				// TODO Async Sitzplätze aktualisieren
				this.rideRepository.save(ride);
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
	public List<Ride> findRidesByDriver(Long driverId) {
		return rideRepository.findRidesByDriverId(driverId);
	}

	/**
	 * Check if there are still free seats in the car.
	 *
	 * @param rideId - The id of the car whose status is to be checked
	 * @return true if there are available seats, false if not
	 */
	public boolean verifyRideSeats(String rideId) {
		Optional<Ride> rideTemp = rideRepository.findById(rideId);
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
		List<Ride> startResult = getAvailableRidesList(Constants.STARTINDEX, ldt, start);
		List<Ride> destinationResult = getAvailableRidesList(Constants.DESTINATIONINDEX, ldt, destination);
		startResult.retainAll(destinationResult);
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
		mapboxGeocoding.getGeodata(address);
		double[] coords = address.getCoordinates();
		Point point = new Point(coords[0], coords[1]);
		List<GeoResult<Ride>> result = rideRepository.findAvailableRides(index, formatted, point).getContent();
		return result.stream().map(GeoResult::getContent).collect(Collectors.toList());
	}

	/**
	 * Checks if the passed time plus the number of days is greater than or equal to the current time
	 *
	 * @param time - The time to be checked
	 * @param days - The days that should be added to the time
	 * @return true if the passed time plus days is greater than or equal to the current time, false if not
	 */
	private boolean validateTime(LocalDateTime time, int days) {
		return time.plusDays(days).isAfter(LocalDateTime.now());
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

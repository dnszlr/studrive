package com.zeller.studrive.offerservice.service;

import com.zeller.studrive.offerservice.helper.MapboxGeocoding;
import com.zeller.studrive.offerservice.model.Address;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.model.RideStatus;
import com.zeller.studrive.offerservice.repository.RideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
		ride.setRideStatus(RideStatus.AVAILABLE);
		mapboxGeocoding.getCoordinates(ride.getStart());
		mapboxGeocoding.getCoordinates(ride.getDestination());
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
	 * Closes the ride that belongs to the passed id by changing its status to closed
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
	 * Returns a list of available rides for the passed values.
	 * <p>
	 * This is not optimal but because there is no UI planned implementing a location picker would be overkill.
	 * Latitude and Longitude queries would be best.
	 * Mapbox is a free tool to do that, if required in the future.
	 *
	 * @param start       - The address where the ride starts
	 * @param destination - The destination where the ride ends
	 * @return The list of available rides for the passed values
	 */
	public List<Ride> findAvailableRide(Address start, Address destination) {
		mapboxGeocoding.getCoordinates(start);
		mapboxGeocoding.getCoordinates(destination);
		return null;
	}

	public List<Ride> findRideByDriver(Long driverId) {
		return rideRepository.findRidesByDriverId(driverId);
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
		return ride.getRideStatus().equals(rideStatus);
	}

}

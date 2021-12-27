package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.model.FindAvailableRequest;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.service.RideService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/rides")
public class OfferController {

	private final RideService rideService;

	public OfferController(RideService rideService) {
		this.rideService = rideService;
	}

	@PostMapping(path = "/")
	public ResponseEntity<OfferRideResponse> offerRide(@RequestBody Ride ride) {
		Optional<Ride> rideTemp = rideService.offerRide(ride);
		return rideTemp.map(value -> new ResponseEntity<>(new OfferRideResponse(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
	}

	@GetMapping(path = "/{rideId}")
	public ResponseEntity<Ride> findRideById(@PathVariable String rideId) {
		Optional<Ride> ride = rideService.findById(rideId);
		return ride.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping(path = "/{rideId}/cancel")
	public ResponseEntity<StatusChangeResponse> cancelRide(@PathVariable String rideId) {
		return createResponseEntity(rideService.cancelRide(rideId));
	}

	@PutMapping(path = "/{rideId}/close")
	public ResponseEntity<StatusChangeResponse> closeRide(@PathVariable String rideId) {
		return createResponseEntity(rideService.closeRide(rideId));
	}

	/**
	 * Creates the appropriate ResponseEntity for the passed optional. If the optional contains null the HttpStatus NOT_FOUND is returned.
	 * If the optional contains an entity, it will be returned with HttpStatus OK.
	 *
	 * @param ride - The optional which contains either a ride or null
	 * @return A new response entity that provides information about the outcome of the operation
	 */
	private ResponseEntity<StatusChangeResponse> createResponseEntity(Optional<Ride> ride) {
		return ride.map(value -> new ResponseEntity<>(new StatusChangeResponse(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PostMapping(path = "/available")
	public List<Ride> findAvailableRide(@RequestBody FindAvailableRequest findAvailableRequest) {
		return rideService.getAvailableRide(findAvailableRequest.getStartDate(), findAvailableRequest.getStart(),
				findAvailableRequest.getDestination());
	}

	@GetMapping(path = "/driver/{driverId}")
	public List<Ride> findRidesByDriver(@PathVariable Long driverId) {
		return rideService.getRidesByDriver(driverId);
	}

	@GetMapping(path = "/{rideId}/seats")
	public boolean verifyRideSeats(@PathVariable String rideId) {
		return rideService.verifyRideSeats(rideId);
	}
}


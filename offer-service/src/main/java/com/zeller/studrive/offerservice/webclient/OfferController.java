package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.model.AvailableRequest;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.service.RideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/rides")
public class OfferController {

	private RideService rideService;

	public OfferController(RideService rideService) {
		this.rideService = rideService;
	}

	@PostMapping(path = "/")
	public Ride offerRide(@RequestBody Ride ride) {
		return rideService.offerRide(ride);
	}

	@PutMapping(path = "/{rideId}/cancel")
	public Optional<Ride> cancelRide(@PathVariable String rideId) {
		return rideService.cancelRide(rideId);
	}

	@PutMapping(path = "/{rideId}/close")
	public Optional<Ride> closeRide(@PathVariable String rideId) {
		return rideService.closeRide(rideId);
	}

	@PostMapping(path = "/available")
	public List<Ride> findAvailableRide(@RequestBody AvailableRequest availableRequest) {
		return rideService.getAvailableRide(availableRequest.getStartDate(), availableRequest.getStart(),
				availableRequest.getDestination());
	}

	@GetMapping(path = "/{driverId}/driver")
	public List<Ride> findRidesByDriver(@PathVariable Long driverId) {
		return rideService.findRidesByDriver(driverId);
	}

	@GetMapping(path = "/{rideId}/seats")
	public boolean verifyRideSeats(@PathVariable String rideId) {
		return rideService.verifyRideSeats(rideId);
	}

}


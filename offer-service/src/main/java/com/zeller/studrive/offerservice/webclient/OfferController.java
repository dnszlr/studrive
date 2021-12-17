package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.model.AvailableResponse;
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
	public List<Ride> getAvailableRides(@RequestBody AvailableResponse availableResponse) {
		rideService.findAvailableRide(availableResponse.getStartDate(), availableResponse.getStart(), availableResponse.getDestination());
		return null;
	}

	@GetMapping(path = "/drivers/{driverId}")
	public List<Ride> getRidesByDriver(@PathVariable Long driverId) {
		return rideService.findRidesByDriver(driverId);
	}

}


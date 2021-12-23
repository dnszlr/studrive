package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.model.FindAvailableRequest;
import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.service.RideService;
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
	public Ride offerRide(@RequestBody Ride ride) {
		return rideService.offerRide(ride);
	}

	// TODO NUR ID UND STATUS
	@PutMapping(path = "/{rideId}/cancel")
	public Optional<Ride> cancelRide(@PathVariable String rideId) {
		return rideService.cancelRide(rideId);
	}

	// TODO NUR ID UND STATUS
	@PutMapping(path = "/{rideId}/close")
	public Optional<Ride> closeRide(@PathVariable String rideId) {
		return rideService.closeRide(rideId);
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


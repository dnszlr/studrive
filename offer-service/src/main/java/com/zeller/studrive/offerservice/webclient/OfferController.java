package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.model.Address;
import com.zeller.studrive.offerservice.service.RideService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/v1/rides")
public class OfferController {

	private RideService rideService;

	public OfferController(RideService rideService) {
		this.rideService = rideService;
	}

	@GetMapping(path = "/")
	public void test() {
		Address address = new Address();
		address.setCity("Wendelsheim");
		address.setHouseNumber(15);
		address.setStreet("Obere Dorfstraße");
		address.setPostalCode("72108");
		rideService.findAvailableRide(address, address);
	}

}


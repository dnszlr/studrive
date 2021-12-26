package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.service.RideService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(controllers = OfferController.class)
class OfferControllerTest {

	@MockBean
	private RideService rideService;
	@Autowired
	private MockMvc mockMvc;

	@Test
	void offerRide() {
	}

	@Test
	void findRideById() {
	}

	@Test
	void cancelRide() {
	}

	@Test
	void closeRide() {
	}

	@Test
	void findAvailableRide() {
	}

	@Test
	void findRidesByDriver() {
	}

	@Test
	void verifyRideSeats() {
	}
}
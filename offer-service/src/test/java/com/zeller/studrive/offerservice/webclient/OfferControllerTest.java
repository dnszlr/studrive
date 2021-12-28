package com.zeller.studrive.offerservice.webclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeller.studrive.offerservice.model.*;
import com.zeller.studrive.offerservice.service.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OfferController.class)
class OfferControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private RideService rideService;
	private Ride ride;

	@BeforeEach
	void setUp() {
		Long driverId = 100L;
		Address start = new Address("Reutlingen", "72762", "Alteburgstraße", 150);
		Address destination = new Address("Berlin", "10117", "Unter den Linden", 77);
		LocalDateTime startDate = LocalDateTime.of(2021, 12, 27, 12, 0);
		LocalDateTime endDate = LocalDateTime.of(2021, 12, 27, 22, 0);
		Car car = new Car("BMW", "TÜ-TE-1337", 4);
		double pricePerSeat = 25.0;
		ride = new Ride(driverId, start, destination, startDate, endDate, car, pricePerSeat);
	}

	@Test
	void canOfferRide() throws Exception {
		Mockito.when(rideService.offerRide(ride)).thenReturn(Optional.of(ride));
		mockMvc.perform(post("/v1/rides/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(ride)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(ride.getId())));
	}

	@Test
	void cantOfferRide() throws Exception {
		Mockito.when(rideService.offerRide(ride)).thenReturn(Optional.empty());
		mockMvc.perform(post("/v1/rides/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(ride)))
				.andExpect(status().isConflict());
	}

	@Test
	void canFindRideById() throws Exception {
		String id = "100R";
		ride.setId(id);
		ride.setRideStatus(RideStatus.AVAILABLE);
		Mockito.when(rideService.findById(id)).thenReturn(Optional.of(ride));
		mockMvc.perform(get("/v1/rides/{rideId}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(ride.getId())))
				.andExpect(jsonPath("$.rideStatus", is(RideStatus.AVAILABLE.toString())));
	}

	@Test
	void cantFindRideById() throws Exception {
		Mockito.when(rideService.findById("200R")).thenReturn(Optional.empty());
		mockMvc.perform(get("/v1/rides/{rideId}", "200R"))
				.andExpect(status().isNotFound());
	}

	@Test
	void canCancelRide() throws Exception {
		String id = "100R";
		ride.setId(id);
		ride.setRideStatus(RideStatus.CANCELED);
		Mockito.when(rideService.cancelRide(id)).thenReturn(Optional.of(ride));
		mockMvc.perform(put("/v1/rides/{rideId}/cancel", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(ride.getId())))
				.andExpect(jsonPath("$.rideStatus", is(RideStatus.CANCELED.toString())));
	}

	@Test
	void cantCancelRide() throws Exception {
		Mockito.when(rideService.cancelRide("200R")).thenReturn(Optional.empty());
		mockMvc.perform(put("/v1/rides/{rideId}/cancel", "200R"))
				.andExpect(status().isNotFound());
	}

	@Test
	void canCloseRide() throws Exception {
		String id = "100R";
		ride.setId(id);
		ride.setRideStatus(RideStatus.CLOSED);
		Mockito.when(rideService.closeRide(id)).thenReturn(Optional.of(ride));
		mockMvc.perform(put("/v1/rides/{rideId}/close", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(ride.getId())))
				.andExpect(jsonPath("$.rideStatus", is(RideStatus.CLOSED.toString())));
	}

	@Test
	void cantCloseRide() throws Exception {
		Mockito.when(rideService.closeRide("200R")).thenReturn(Optional.empty());
		mockMvc.perform(put("/v1/rides/{rideId}/close", "200R"))
				.andExpect(status().isNotFound());
	}

	@Test
	void canFindAvailableRide() throws Exception {
		List<Ride> dummy = Arrays.asList(ride, ride, ride);
		FindAvailableRequest far = new FindAvailableRequest(ride.getStart(), ride.getDestination(), LocalDate.of(2021, 12, 27));
		Mockito.when(rideService.getAvailableRide(far.getStartDate(), far.getStart(), far.getDestination())).thenReturn(dummy);
		mockMvc.perform(post("/v1/rides/available")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(far)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(dummy.size())));
	}

	@Test
	void cantFindAvailableRide() throws Exception {
		FindAvailableRequest far = new FindAvailableRequest(ride.getStart(), ride.getDestination(), LocalDate.of(2022, 2, 12));
		Mockito.when(rideService.getAvailableRide(far.getStartDate(), far.getStart(), far.getDestination())).thenReturn(Collections.emptyList());
		mockMvc.perform(post("/v1/rides/available")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(far)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void canFindRidesByDriver() throws Exception {
		List<Ride> dummy = Arrays.asList(ride, ride, ride);
		Mockito.when(rideService.getRidesByDriver(ride.getDriverId())).thenReturn(dummy);
		mockMvc.perform(get("/v1/rides/driver/{driverId}", ride.getDriverId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(dummy.size())));
	}

	@Test
	void cantFindRidesByDriver() throws Exception {
		Mockito.when(rideService.getRidesByDriver(200L)).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/v1/rides/driver/{driverId}", 200L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void canVerifyRideSeats() throws Exception {
		String id = "100R";
		ride.setId(id);
		Mockito.when((rideService.verifyRideSeats(ride.getId()))).thenReturn(true);
		mockMvc.perform(get("/v1/rides/{rideId}/seats", ride.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(true)));
	}

	@Test
	void cantVerifyRideSeats() throws Exception {
		String id = "100R";
		ride.setId(id);
		Mockito.when((rideService.verifyRideSeats(ride.getId()))).thenReturn(false);
		mockMvc.perform(get("/v1/rides/{rideId}/seats", ride.getId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(false)));
	}
}
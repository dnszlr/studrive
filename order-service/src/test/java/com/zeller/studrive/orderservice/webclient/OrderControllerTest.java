package com.zeller.studrive.orderservice.webclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.model.SeatStatus;
import com.zeller.studrive.orderservice.service.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private SeatService seatService;
	private Seat seat;

	@BeforeEach
	void setUp() {
		Long passengerId = 100L;
		String rideId = "100R";
		seat = new Seat(passengerId, rideId);
	}

	@Test
	void canBookSeat() throws Exception {
		Mockito.when(seatService.bookSeat(seat)).thenReturn(Optional.of(seat));
		mockMvc.perform(post("/v1/seats/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(seat)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(seat.getId())));
	}

	@Test
	void cantBookSeat() throws Exception {
		Mockito.when(seatService.bookSeat(seat)).thenReturn(Optional.empty());
		mockMvc.perform(post("/v1/seats/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(seat)))
				.andExpect(status().isConflict());
	}

	@Test
	void canFindRideById() throws Exception {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.PENDING);
		Mockito.when(seatService.findById(id)).thenReturn(Optional.of(seat));
		mockMvc.perform(get("/v1/seats/{seatId}", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(seat.getId())))
				.andExpect(jsonPath("$.seatStatus", is(seat.getSeatStatus().toString())));
	}

	@Test
	void cantFindRideById() throws Exception {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.PENDING);
		Mockito.when(seatService.findById("200S")).thenReturn(Optional.empty());
		mockMvc.perform(get("/v1/seats/{seatId}", id))
				.andExpect(status().isNotFound());
	}

	@Test
	void canCancelSeat() throws Exception {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.CANCELED);
		Mockito.when(seatService.cancelSeat(id)).thenReturn(Optional.of(seat));
		mockMvc.perform(put("/v1/seats/{seatId}/cancel", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(seat.getId())))
				.andExpect(jsonPath("$.seatStatus", is(SeatStatus.CANCELED.toString())));
	}

	@Test
	void cantCancelSeat() throws Exception {
		Mockito.when(seatService.cancelSeat("200S")).thenReturn(Optional.empty());
		mockMvc.perform(put("/v1/seats/{seatId}/cancel", "200S"))
				.andExpect(status().isNotFound());
	}

	@Test
	void canAcceptSeat() throws Exception {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.ACCEPTED);
		Mockito.when(seatService.acceptSeat(id)).thenReturn(Optional.of(seat));
		mockMvc.perform(put("/v1/seats/{seatId}/accept", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(seat.getId())))
				.andExpect(jsonPath("$.seatStatus", is(SeatStatus.ACCEPTED.toString())));
	}

	@Test
	void cantAcceptSeat() throws Exception {
		Mockito.when(seatService.acceptSeat("200S")).thenReturn(Optional.empty());
		mockMvc.perform(put("/v1/seats/{seatId}/accept", "200S"))
				.andExpect(status().isNotFound());
	}

	@Test
	void canDeclineSeat() throws Exception {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.DENIED);
		Mockito.when(seatService.declineSeat(id)).thenReturn(Optional.of(seat));
		mockMvc.perform(put("/v1/seats/{seatId}/decline", id))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(seat.getId())))
				.andExpect(jsonPath("$.seatStatus", is(SeatStatus.DENIED.toString())));
	}

	@Test
	void cantDeclineSeat() throws Exception {
		Mockito.when(seatService.declineSeat("200S")).thenReturn(Optional.empty());
		mockMvc.perform(put("/v1/seats/{seatId}/decline", "200S"))
				.andExpect(status().isNotFound());
	}

	@Test
	void canFindSeatsByPassenger() throws Exception {
		List<Seat> dummy = Arrays.asList(seat, new Seat(100L, "200R"), new Seat(100L, "300R"));
		Mockito.when(seatService.getSeatsByPassenger(seat.getPassengerId())).thenReturn(dummy);
		mockMvc.perform(get("/v1/seats/passenger/{passengerId}", seat.getPassengerId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(dummy.size())));
	}

	@Test
	void cantFindSeatsByPassenger() throws Exception {
		Mockito.when(seatService.getSeatsByPassenger(seat.getPassengerId())).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/v1/seats/passenger/{passengerId}", 500L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}

	@Test
	void canFindSeatsByRide() throws Exception {
		List<Seat> dummy = Arrays.asList(seat, new Seat(200L, "100R"), new Seat(300L, "100R"));
		Mockito.when(seatService.getSeatsByRide(seat.getRideId())).thenReturn(dummy);
		mockMvc.perform(get("/v1/seats/ride/{rideId}", seat.getRideId()))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(dummy.size())));
	}

	@Test
	void cantFindSeatsByRide() throws Exception {
		Mockito.when(seatService.getSeatsByRide("700R")).thenReturn(Collections.emptyList());
		mockMvc.perform(get("/v1/seats/ride/{rideId}", "700R"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(0)));
	}
}
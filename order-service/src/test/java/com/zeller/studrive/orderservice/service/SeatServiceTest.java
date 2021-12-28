package com.zeller.studrive.orderservice.service;

import com.zeller.studrive.orderservice.basic.RequestClient;
import com.zeller.studrive.orderservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.model.SeatStatus;
import com.zeller.studrive.orderservice.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class SeatServiceTest {

	private SeatService testService;

	@Mock
	private SeatRepository seatRepository;
	@Mock
	private TaskSender taskSender;
	@Mock
	private RequestClient requestClient;

	private Seat seat;

	@BeforeEach
	void setUp() {
		testService = new SeatService(seatRepository, taskSender, requestClient);
		Long passengerId = 100L;
		String rideId = "100R";
		seat = new Seat(passengerId, rideId);
	}

	@Test
	void canSaveAll() {
		List<Seat> seatList = Collections.singletonList(seat);
		testService.saveAll(seatList);
		verify(seatRepository).saveAll(seatList);
	}

	@Test
	void canSave() {
		testService.save(seat);
		verify(seatRepository).save(seat);
	}

	@Test
	void canBookSeat() {
		Mockito.when(requestClient.verifyRideSeats(seat.getRideId())).thenReturn(true);
		Mockito.when(requestClient.verifyPaymentDetail(seat.getPassengerId())).thenReturn(true);
		Mockito.when(seatRepository.save(seat)).thenReturn(seat);
		Optional<Seat> result = testService.bookSeat(seat);
		verify(requestClient).verifyRideSeats(seat.getRideId());
		verify(requestClient).verifyPaymentDetail(seat.getPassengerId());
		verify(seatRepository).save(seat);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getSeatStatus()).isEqualTo(SeatStatus.PENDING);
	}

	@Test
	void cantBookSeat() {
		Mockito.when(requestClient.verifyRideSeats(seat.getRideId())).thenReturn(true);
		Mockito.when(requestClient.verifyPaymentDetail(seat.getPassengerId())).thenReturn(false);
		Optional<Seat> result = testService.bookSeat(seat);
		verify(requestClient).verifyRideSeats(seat.getRideId());
		verify(requestClient).verifyPaymentDetail(seat.getPassengerId());
		assertThat(result.isPresent()).isFalse();
		assertThat(seat.getSeatStatus()).isEqualTo(null);
	}

	@Test
	void canFindById() {
		String id = "100S";
		seat.setId(id);
		Mockito.when(seatRepository.findSeatById(id)).thenReturn(Optional.of(seat));
		Optional<Seat> result = testService.findById(id);
		verify(seatRepository).findSeatById(id);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(seat);
	}

	@Test
	void canCancelSeat() {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.ACCEPTED);
		Mockito.when(seatRepository.findSeatById(id)).thenReturn(Optional.of(seat));
		Optional<Seat> result = testService.cancelSeat(id);
		verify(seatRepository).save(seat);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getSeatStatus()).isEqualTo(SeatStatus.CANCELED);
	}

	@Test
	void canAcceptSeat() {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.PENDING);
		Mockito.when(seatRepository.findSeatById(id)).thenReturn(Optional.of(seat));
		Mockito.when(requestClient.verifyRideSeats(seat.getRideId())).thenReturn(true);
		Optional<Seat> result = testService.acceptSeat(id);
		verify(seatRepository).save(seat);
		assertThat(result.isPresent()).isTrue();
		assertThat(seat.getSeatStatus()).isEqualTo(SeatStatus.ACCEPTED);
	}

	@Test
	void cantAcceptSeat() {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.PENDING);
		Mockito.when(seatRepository.findSeatById(id)).thenReturn(Optional.of(seat));
		Mockito.when(requestClient.verifyRideSeats(seat.getRideId())).thenReturn(false);
		Optional<Seat> result = testService.acceptSeat(id);
		verify(seatRepository).findSeatById(id);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getSeatStatus()).isEqualTo(SeatStatus.PENDING);
		assertThat(seat.getSeatStatus()).isEqualTo(SeatStatus.PENDING);
	}

	@Test
	void canDeclineSeat() {
		String id = "100S";
		seat.setId(id);
		seat.setSeatStatus(SeatStatus.PENDING);
		Mockito.when(seatRepository.findSeatById(id)).thenReturn(Optional.of(seat));
		Optional<Seat> result = testService.declineSeat(id);
		verify(seatRepository).findSeatById(id);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get().getSeatStatus()).isEqualTo(SeatStatus.DENIED);
		assertThat(seat.getSeatStatus()).isEqualTo(SeatStatus.DENIED);
	}

	@Test
	void canGetSeatsByPassenger() {
		List<Seat> resultList = testService.getSeatsByPassenger(seat.getPassengerId());
		verify(seatRepository).findSeatsByPassengerId(seat.getPassengerId());
	}

	@Test
	void canGetSeatsByRide() {
		List<Seat> resultList = testService.getSeatsByRide(seat.getRideId());
		verify(seatRepository).findSeatsByRideId(seat.getRideId());
	}
}
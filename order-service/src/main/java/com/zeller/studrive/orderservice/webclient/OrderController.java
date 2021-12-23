package com.zeller.studrive.orderservice.webclient;

import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.service.SeatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/seats")
public class OrderController {

	private final SeatService seatService;

	public OrderController(SeatService seatService) {
		this.seatService = seatService;
	}

	@PostMapping(path = "/")
	public ResponseEntity<BookSeatResponse> bookSeat(@RequestBody Seat seat) {
		Optional<Seat> seatTemp = seatService.bookSeat(seat);
		return seatTemp.map(value -> new ResponseEntity<>(new BookSeatResponse(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
	}

	@PutMapping(path = "/{seatId}/cancel")
	public ResponseEntity<StatusChangeResponse> cancelSeat(@PathVariable String seatId) {
		return createResponseEntity(seatService.cancelSeat(seatId));
	}

	@PutMapping(path = "/{seatId}/accept")
	public ResponseEntity<StatusChangeResponse> acceptSeat(@PathVariable String seatId) {
		return createResponseEntity(seatService.acceptSeat(seatId));
	}

	@PutMapping(path = "/{seatId}/decline")
	public ResponseEntity<StatusChangeResponse> declineSeat(@PathVariable String seatId) {
		return createResponseEntity(seatService.declineSeat(seatId));
	}

	private ResponseEntity<StatusChangeResponse> createResponseEntity(Optional<Seat> seat) {
		return seat.map(value -> new ResponseEntity<>(new StatusChangeResponse(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping(path = "/passenger/{passengerId}")
	public List<Seat> findSeatsByPassenger(@PathVariable Long passengerId) {
		return seatService.getSeatsByPassenger(passengerId);
	}

	@GetMapping(path = "/ride/{rideId}")
	public List<Seat> findSeatsByRide(@PathVariable String rideId) {
		return seatService.getSeatsByRide(rideId);
	}
}

package com.zeller.studrive.orderservice.webclient;

import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/seats")
public class OrderController {

	private final Logger logger = LoggerFactory.getLogger(OrderController.class);
	private final SeatService seatService;

	public OrderController(SeatService seatService) {
		this.seatService = seatService;
	}

	@PostMapping(path = "/")
	public ResponseEntity<BookSeatResponse> bookSeat(@RequestBody Seat seat) {
		logger.info("Received bookSeat request: " + seat);
		Optional<Seat> seatTemp = seatService.bookSeat(seat);
		return seatTemp.map(value -> new ResponseEntity<>(new BookSeatResponse(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.CONFLICT));
	}

	@GetMapping(path = "/{seatId}")
	public ResponseEntity<Seat> findSeatById(@PathVariable String seatId) {
		logger.info("Received findSeatById request with id: " + seatId);
		Optional<Seat> seat = seatService.findById(seatId);
		return seat.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping(path = "/{seatId}/cancel")
	public ResponseEntity<StatusChangeResponse> cancelSeat(@PathVariable String seatId) {
		logger.info("Received cancelSeat request with id: " + seatId);
		return createResponseEntity(seatService.cancelSeat(seatId));
	}

	@PutMapping(path = "/{seatId}/accept")
	public ResponseEntity<StatusChangeResponse> acceptSeat(@PathVariable String seatId) {
		logger.info("Received acceptSeat request with id: " + seatId);
		return createResponseEntity(seatService.acceptSeat(seatId));
	}

	@PutMapping(path = "/{seatId}/decline")
	public ResponseEntity<StatusChangeResponse> declineSeat(@PathVariable String seatId) {
		logger.info("Received declineSeat request with id: " + seatId);
		return createResponseEntity(seatService.declineSeat(seatId));
	}

	/**
	 * Creates the appropriate ResponseEntity for the passed optional. If the optional contains null the HttpStatus NOT_FOUND is returned.
	 * If the optional contains an entity, it will be returned with HttpStatus OK.
	 *
	 * @param seat - The optional which contains either a seat or null
	 * @return A new response entity that provides information about the outcome of the operation
	 */
	private ResponseEntity<StatusChangeResponse> createResponseEntity(Optional<Seat> seat) {
		return seat.map(value -> new ResponseEntity<>(new StatusChangeResponse(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping(path = "/passenger/{passengerId}")
	public List<Seat> findSeatsByPassenger(@PathVariable Long passengerId) {
		logger.info("Received findSeatsByPassenger request with passengerId: " + passengerId);
		return seatService.getSeatsByPassenger(passengerId);
	}

	@GetMapping(path = "/ride/{rideId}")
	public List<Seat> findSeatsByRide(@PathVariable String rideId) {
		logger.info("Received findSeatsByRide request with id: " + rideId);
		return seatService.getSeatsByRide(rideId);
	}
}

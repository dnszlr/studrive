package com.zeller.studrive.orderservice.webclient;

import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.service.SeatService;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
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
	public Seat bookSeat(@RequestBody Seat seat) {
		return seatService.bookSeat(seat);
	}

	@PutMapping(path = "/{seatId}/cancel")
	public Optional<Seat> cancelSeat(@PathVariable String seatId) {
		return seatService.cancelSeat(seatId);
	}

	@PutMapping(path = "/{seatId}/accept")
	public Optional<Seat> acceptSeat(@PathVariable String seatId) {
		return seatService.acceptSeat(seatId);
	}

	@PutMapping(path = "/{seatId}/decline")
	public Optional<Seat> declineSeat(@PathVariable String seatId) {
		return seatService.declineSeat(seatId);
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

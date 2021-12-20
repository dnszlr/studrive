package com.zeller.studrive.orderservice.service;

import com.zeller.studrive.orderservice.basic.RequestClient;
import com.zeller.studrive.orderservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.model.SeatStatus;
import com.zeller.studrive.orderservice.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

	@Autowired
	private SeatRepository seatRepository;
	@Autowired
	private TaskSender taskSender;
	@Autowired
	private RequestClient requestClient;

	/**
	 * Checks if the passed seat is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param seat - the seat to be saved
	 * @return the newly created seat
	 */
	public Seat bookSeat(Seat seat) {
		if(requestClient.verifyRideSeats(seat.getRideId()) && requestClient.verifyPaymentDetail(seat.getPassengerId())) {
			seat = seatRepository.save(seat);
		}
		// TODO Keine optimale Lösung
		return seat;
	}

	public Optional<Seat> cancelSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			boolean validStatus = false;
			if(checkSeatStatus(seat, SeatStatus.ACCEPTED)) {
				taskSender.cancelOperation(seat.getId());
				validStatus = true;
			}
			if(validStatus || checkSeatStatus(seat, SeatStatus.PENDING)) {
				seat.setSeatStatus(SeatStatus.CANCELED);
				seatRepository.save(seat);
			}
		}
		return seatTemp;
	}

	public Optional<Seat> acceptSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			seat.setSeatStatus(SeatStatus.ACCEPTED);
			seatRepository.save(seat);
			taskSender.acceptOperation(seat.getId());
		}
		return seatTemp;
	}

	public Optional<Seat> declineSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			if(checkSeatStatus(seat, SeatStatus.PENDING)) {
				seat.setSeatStatus(SeatStatus.DENIED);
				seatRepository.save(seat);
				taskSender.declineOperation(seat.getId());
			}
		}
		return seatTemp;
	}

	public List<Seat> getSeatsByPassenger(Long passengerId) {
		return seatRepository.findSeatsByPassengerId(passengerId);
	}

	public List<Seat> getSeatsByRide(String rideId) {
		return seatRepository.findSeatsByRideId(rideId);
	}

	/**
	 * Checks if the passed seat has the passed seatStatus
	 *
	 * @param seat       - The seat to be checked
	 * @param seatStatus - The seatStatus searched for
	 * @return true if the passed seat has the seatStatus, false if not
	 */
	private boolean checkSeatStatus(Seat seat, SeatStatus seatStatus) {
		return seat.getSeatStatus() == seatStatus;
	}
}

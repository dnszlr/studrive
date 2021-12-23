package com.zeller.studrive.orderservice.service;

import com.zeller.studrive.accoutingservicemq.eventmodel.CancelAccount;
import com.zeller.studrive.accoutingservicemq.eventmodel.CreateAccount;
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

	public List<Seat> saveAll(List<Seat> seats) {
		return seatRepository.saveAll(seats);
	}

	/**
	 * Checks if the passed seat is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param seat - the seat to be saved
	 * @return the newly created seat
	 */
	public Optional<Seat> bookSeat(Seat seat) {
		Optional<Seat> seatTemp = Optional.empty();
		if(requestClient.verifyRideSeats(seat.getRideId()) && requestClient.verifyPaymentDetail(seat.getPassengerId())) {
			seatTemp = Optional.of(seatRepository.save(seat));
		}
		return seatTemp;
	}

	public Optional<Seat> findById(String seatId) {
		return seatRepository.findSeatById(seatId);
	}

	public Optional<Seat> cancelSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findSeatById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			boolean acceptedStatus = checkSeatStatus(seat, SeatStatus.ACCEPTED);
			if(acceptedStatus || checkSeatStatus(seat, SeatStatus.PENDING)) {
				seat.setSeatStatus(SeatStatus.CANCELED);
				seatRepository.save(seat);
				if(acceptedStatus) {
					taskSender.cancelSeat(seat.getRideId(), new CancelAccount(seat.getId()));
				}
			}
		}
		return seatTemp;
	}

	public Optional<Seat> acceptSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findSeatById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			seat.setSeatStatus(SeatStatus.ACCEPTED);
			seatRepository.save(seat);
			String rideId = seat.getRideId();
			CreateAccount createAccount = new CreateAccount(seat.getPassengerId(), seat.getId());
			taskSender.acceptSeat(rideId, createAccount, getCurrentSeats(rideId));
		}
		return seatTemp;
	}

	public Optional<Seat> declineSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findSeatById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			if(checkSeatStatus(seat, SeatStatus.PENDING)) {
				seat.setSeatStatus(SeatStatus.DENIED);
				seatRepository.save(seat);
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

	private int getCurrentSeats(String rideId) {
		return seatRepository.findSeatsByRideId(rideId).size();
	}
}

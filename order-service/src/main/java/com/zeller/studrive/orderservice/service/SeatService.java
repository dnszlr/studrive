package com.zeller.studrive.orderservice.service;

import com.zeller.studrive.accoutingservicemq.eventmodel.CancelAccount;
import com.zeller.studrive.accoutingservicemq.eventmodel.CreateAccount;
import com.zeller.studrive.orderservice.basic.RequestClient;
import com.zeller.studrive.orderservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.model.SeatStatus;
import com.zeller.studrive.orderservice.repository.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	final Logger logger = LoggerFactory.getLogger(SeatService.class);

	/**
	 * Saves all passed seats
	 *
	 * @param seats - The list of seats
	 * @return A list of saved seats
	 */
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
		// If the ride has available seats and the user has already deposited payment information, a seat can be booked.
		if(requestClient.verifyRideSeats(seat.getRideId()) && requestClient.verifyPaymentDetail(seat.getPassengerId())) {
			seat.setSeatStatus(SeatStatus.PENDING);
			seatTemp = Optional.of(seatRepository.save(seat));
			logger.info("SeatService.bookSeat: New Seat with the id " + seat.getId() + " and SeatStatus " + seat.getSeatStatus() + " " +
					"booked");
		}
		return seatTemp;
	}

	/**
	 * Returns the seat matching the passed id
	 *
	 * @param seatId - The id of the requested seat
	 * @return The seat or null
	 */
	public Optional<Seat> findById(String seatId) {
		return seatRepository.findSeatById(seatId);
	}

	/**
	 * Cancels a seat that has already been booked
	 *
	 * @param seatId - The id of the seat to be canceled
	 * @return The canceled seat or null
	 */
	public Optional<Seat> cancelSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findSeatById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			boolean acceptedStatus = checkSeatStatus(seat, SeatStatus.ACCEPTED);
			if(acceptedStatus || checkSeatStatus(seat, SeatStatus.PENDING)) {
				seat.setSeatStatus(SeatStatus.CANCELED);
				// TODO wie hier überprüfen ob der Sitzplatz gespeichert wurde?
				seatRepository.save(seat);
				logger.info("SeatService.cancelSeat: Seat with the id " + seat.getId() + " got " + seat.getSeatStatus());
				if(acceptedStatus) {
					taskSender.cancelSeat(seat.getRideId(), new CancelAccount(seat.getId()));
				}
			}
		}
		return seatTemp;
	}

	/**
	 * Accepts a seat that has already been booked
	 * This is necessary because a driver can choose if he wants to accept or decline a seat
	 *
	 * @param seatId - The id of the seat to be canceled
	 * @return The accepted seat or null
	 */
	public Optional<Seat> acceptSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findSeatById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			if(checkSeatStatus(seat, SeatStatus.PENDING) && requestClient.verifyRideSeats(seat.getRideId())) {
				seat.setSeatStatus(SeatStatus.ACCEPTED);
				// TODO wie hier überprüfen ob der Sitzplatz gespeichert wurde?
				seatRepository.save(seat);
				logger.info("SeatService.acceptSeat: Seat with the id " + seat.getId() + " got " + seat.getSeatStatus());
				String rideId = seat.getRideId();
				CreateAccount createAccount = new CreateAccount(seat.getPassengerId(), seat.getId());
				taskSender.acceptSeat(rideId, createAccount, getCurrentSeats(rideId));
			}
		}
		return seatTemp;
	}

	/**
	 * Declines a seat that has already been booked
	 * This is necessary because a driver can choose if he wants to accept or decline a seat
	 *
	 * @param seatId - The id of the seat to be canceled
	 * @return The accepted seat or null
	 */
	public Optional<Seat> declineSeat(String seatId) {
		Optional<Seat> seatTemp = seatRepository.findSeatById(seatId);
		if(seatTemp.isPresent()) {
			Seat seat = seatTemp.get();
			if(checkSeatStatus(seat, SeatStatus.PENDING)) {
				seat.setSeatStatus(SeatStatus.DENIED);
				// TODO wie hier überprüfen ob der Sitzplatz gespeichert wurde?
				seatRepository.save(seat);
				logger.info("SeatService.declineSeat: Seat with the id " + seat.getId() + " got " + seat.getSeatStatus());
			}
		}
		return seatTemp;
	}

	/**
	 * Returns all seats that are related to the given passenger
	 *
	 * @param passengerId - The id of the passenger
	 * @return A list of seats
	 */
	public List<Seat> getSeatsByPassenger(Long passengerId) {
		return seatRepository.findSeatsByPassengerId(passengerId);
	}

	/**
	 * Returns all seats that are related to the given ride
	 *
	 * @param rideId - The id of the ride
	 * @return A list of seats
	 */
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

	/**
	 * Checks how many seats have already been accepted for the transferred trip
	 *
	 * @param rideId - the id of the ride whose seats are to be checked
	 * @return The amount of seats that already been accepted
	 */
	private int getCurrentSeats(String rideId) {
		return seatRepository.findSeatsByRideIdAndSeatStatus(rideId, SeatStatus.ACCEPTED).size();
	}
}

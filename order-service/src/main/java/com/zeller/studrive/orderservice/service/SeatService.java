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
import org.springframework.amqp.AmqpException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {

	private final SeatRepository seatRepository;
	private final TaskSender taskSender;
	private final RequestClient requestClient;
	final Logger logger = LoggerFactory.getLogger(SeatService.class);

	public SeatService(SeatRepository seatRepository, TaskSender taskSender, RequestClient requestClient) {
		this.seatRepository = seatRepository;
		this.taskSender = taskSender;
		this.requestClient = requestClient;
	}

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
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public Seat save(Seat seat) {
		return seatRepository.save(seat);
	}

	/**
	 * Books a new seat with the passed attribute
	 *
	 * @param seat - the seat to be booked
	 * @return the newly created seat or null
	 */
	public Optional<Seat> bookSeat(Seat seat) {
		Optional<Seat> seatTemp = Optional.empty();
		// If the ride has available seats and the user has already deposited payment information, a seat can be booked.
		if(requestClient.verifyRideSeats(seat.getRideId()) && requestClient.verifyPaymentDetail(seat.getPassengerId())) {
			seat.setSeatStatus(SeatStatus.PENDING);
			seatTemp = Optional.of(this.save(seat));
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
				try {
					this.save(seat);
					logger.info("SeatService.cancelSeat: Seat with the id " + seat.getId() + " got " + seat.getSeatStatus());
					if(acceptedStatus) {
						taskSender.cancelSeat(seat.getRideId(), new CancelAccount(seat.getId()));
					}
				} catch(Exception ex) {
					seatTemp = Optional.empty();
					logger.info("SeatService.cancelSeat: Problems canceling Seat with the id " + seat.getId(), ex);
				}
			}
		}
		return seatTemp;
	}

	/**
	 * Calls the accounting-service to cancel an accounting
	 *
	 * @param cancelAccount - The Rabbitmq message object that contains all the information for the accounting service
	 */
	public void cancelAccounting(CancelAccount cancelAccount) {
		try {
			taskSender.cancelAccounting(cancelAccount);
		} catch(AmqpException aex) {
			logger.info("SeatService.cancelAccounting: Problems canceling Accounting for Seat" + cancelAccount.getSeatId(), aex);
		}
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
				try {
					this.save(seat);
					logger.info("SeatService.acceptSeat: Seat with the id " + seat.getId() + " got " + seat.getSeatStatus());
					String rideId = seat.getRideId();
					CreateAccount createAccount = new CreateAccount(seat.getPassengerId(), seat.getId());
					taskSender.acceptSeat(rideId, createAccount, getCurrentSeats(rideId));
				} catch(Exception ex) {
					seatTemp = Optional.empty();
					logger.info("SeatService.acceptSeat: Problem accepting the Seat with the id " + seat.getId(), ex);
				}
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
				try {
					this.save(seat);
					logger.info("SeatService.declineSeat: Seat with the id " + seat.getId() + " got " + seat.getSeatStatus());
				} catch(Exception ex) {
					seatTemp = Optional.empty();
					logger.info("SeatService.declineSeat: Problem declining the Seat with the id " + seat.getId(), ex);

				}
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

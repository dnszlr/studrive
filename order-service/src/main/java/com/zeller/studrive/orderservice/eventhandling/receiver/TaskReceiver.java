package com.zeller.studrive.orderservice.eventhandling.receiver;

import com.zeller.studrive.accoutingservicemq.eventmodel.CancelAccount;
import com.zeller.studrive.offerservicemq.basic.RabbitMQConstant;
import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.model.SeatStatus;
import com.zeller.studrive.orderservice.service.SeatService;
import com.zeller.studrive.orderservicemq.basic.Operation;
import com.zeller.studrive.orderservicemq.eventmodel.UpdateSeats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TaskReceiver {

	final Logger logger = LoggerFactory.getLogger(TaskReceiver.class);
	@Autowired
	private SeatService seatService;

	/**
	 * Subscribes to the RabbitMQ query through which a message is transmitted from the offer-service if a ride is cancelled.
	 *
	 * @param updateSeats - RabbitMQ message object that contains all the required information.
	 */
	@RabbitListener(queues = RabbitMQConstant.CANCEL_SEATS_QUEUE)
	public void cancelSeats(UpdateSeats updateSeats) {
		if(updateSeats.getOperation() == Operation.CANCEL) {
			List<Seat> seats = seatService.getSeatsByRide(updateSeats.getRideId());
			cancelAccounting(seats);
			updateSeats(seats, SeatStatus.RIDE_CANCELED);
		} else {
			logger.info("TaskReceiver.cancelSeats: Wrong seat passed: " + updateSeats.getOperation() + " passed " + Operation.CANCEL +
					" " + "expected");
		}
	}

	/**
	 * Subscribes to the RabbitMQ query through which a message is transmitted from the offer-service if a ride is closed.
	 *
	 * @param updateSeats - RabbitMQ message object that contains all the required information.
	 */
	@RabbitListener(queues = RabbitMQConstant.CLOSE_SEATS_QUEUE)
	public void closeSeats(UpdateSeats updateSeats) {
		if(updateSeats.getOperation() == Operation.CLOSE) {
			List<Seat> seats = seatService.getSeatsByRide(updateSeats.getRideId());
			updateSeats(seats, SeatStatus.RIDE_CLOSED);
		} else {
			logger.info("TaskReceiver.closeSeats: Wrong seat passed: " + updateSeats.getOperation() + " passed " + Operation.CLOSE + " " +
					"expected");
		}
	}

	/**
	 * Cancel all accountings for seats with status accepted
	 * @param seats - The seats whose accountings should be canceled
	 */
	private void cancelAccounting(List<Seat> seats) {
		seats.stream().filter(seat -> seat.getSeatStatus() == SeatStatus.ACCEPTED)
				.forEach(match -> seatService.cancelAccounting(new CancelAccount(match.getId())));
	}

	/**
	 * Search all seats for the given ride and update their status
	 *
	 * @param seats      - The seats whose status should be updated
	 * @param seatStatus - The status to be given to the seats
	 */
	private void updateSeats(List<Seat> seats, SeatStatus seatStatus) {
		seats.forEach(seat -> seat.setSeatStatus(seatStatus));
		seatService.saveAll(seats);
	}
}

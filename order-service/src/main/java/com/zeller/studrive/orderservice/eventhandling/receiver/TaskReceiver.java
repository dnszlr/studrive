package com.zeller.studrive.orderservice.eventhandling.receiver;

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
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
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
		// TODO Für alle deren Status akzeptiert ist, wird die Rechnung gecancelt
		if(updateSeats.getOperation() == Operation.CANCEL) {
			updateSeats(updateSeats.getRideId(), SeatStatus.RIDE_CANCELED);
		} else {
			logger.info("TaskReceiver.cancelSeats: Wrong seat passed: " + updateSeats.getOperation() + " passed " + Operation.CANCEL + " " +
					"expected");
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
			updateSeats(updateSeats.getRideId(), SeatStatus.RIDE_CLOSED);
		} else {
			logger.info("TaskReceiver.closeSeats: Wrong seat passed: " + updateSeats.getOperation() + " passed " + Operation.CLOSE + " " +
					"expected");
		}
	}

	/**
	 * Search all seats for the given ride and update their status
	 *
	 * @param rideId     - The ride for which all seats are to be searched for
	 * @param seatStatus - The status to be given to the seats
	 */
	private void updateSeats(String rideId, SeatStatus seatStatus) {
		// TODO Nur für die deren Status akzeptiert ist
		List<Seat> seats = seatService.getSeatsByRide(rideId);
		seats.forEach(seat -> seat.setSeatStatus(seatStatus));
		seatService.saveAll(seats);
	}
}

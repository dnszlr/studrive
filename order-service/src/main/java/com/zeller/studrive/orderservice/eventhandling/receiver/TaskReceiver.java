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

import java.util.List;
import java.util.Optional;

public class TaskReceiver {

	Logger logger = LoggerFactory.getLogger(TaskReceiver.class);
	@Autowired
	private SeatService seatService;

	@RabbitListener(queues = RabbitMQConstant.CANCEL_SEATS_QUEUE)
	public void cancelSeats(UpdateSeats updateSeats) {
		if(updateSeats.getOperation() == Operation.CANCEL) {
			updateSeats(updateSeats.getRideId(), SeatStatus.CANCELED);
		} else {
			logger.info("Wrong seat passed: " + updateSeats.getOperation() + " passed " + Operation.CANCEL + " expected");
		}
	}

	@RabbitListener(queues = RabbitMQConstant.CLOSE_SEATS_QUEUE)
	public void closeSeats(UpdateSeats updateSeats) {
		if(updateSeats.getOperation() == Operation.CLOSE) {
			updateSeats(updateSeats.getRideId(), SeatStatus.RIDE_CLOSED);
		} else {
			logger.info("Wrong seat passed: " + updateSeats.getOperation() + " passed " + Operation.CLOSE + " expected");
		}
	}

	private void updateSeats(String rideId, SeatStatus seatStatus) {
		List<Seat> seats = seatService.getSeatsByRide(rideId);
		seats.forEach(seat -> seat.setSeatStatus(seatStatus));
		seatService.saveAll(seats);
	}
}

package com.zeller.studrive.offerservice.eventhandling.sender;

import com.zeller.studrive.offerservicemq.basic.RabbitMQConstant;
import com.zeller.studrive.orderservicemq.basic.Operation;
import com.zeller.studrive.orderservicemq.eventmodel.UpdateSeats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskSender {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange directExchange;

	final Logger logger = LoggerFactory.getLogger(TaskSender.class);

	/**
	 * Notifies the order-service that a ride has been canceled
	 *
	 * @param rideId - The id of the ride that has been canceled
	 */
	public void cancelRide(String rideId) {
		logger.info("TaskSender.cancelRide: RabbitMQ message send to order-service");
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CANCEL_SEATS_KEY, new UpdateSeats(rideId, Operation.CANCEL));
	}

	/**
	 * Notifies the order-service that a ride has been closed
	 *
	 * @param rideId - The id of the ride that has been closed
	 */
	public void closeRide(String rideId) {
		logger.info("TaskSender.closeRide: RabbitMQ message send to order-service");
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CLOSE_SEATS_KEY, new UpdateSeats(rideId, Operation.CLOSE));
	}
}

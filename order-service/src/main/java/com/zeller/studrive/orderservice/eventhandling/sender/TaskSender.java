package com.zeller.studrive.orderservice.eventhandling.sender;

import com.zeller.studrive.accoutingservicemq.eventmodel.CancelAccount;
import com.zeller.studrive.accoutingservicemq.eventmodel.CreateAccount;
import com.zeller.studrive.offerservicemq.eventmodel.FreeRide;
import com.zeller.studrive.offerservicemq.eventmodel.OccupyRide;
import com.zeller.studrive.orderservicemq.basic.RabbitMQConstant;
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
	Logger logger = LoggerFactory.getLogger(TaskSender.class);

	/**
	 * Notifies the accounting and offer-service that a seat has been canceled
	 *
	 * @param rideId        - The id of the ride to which the seat belongs
	 * @param cancelAccount - The Rabbitmq message object that contains all the information for the accounting service
	 */
	public void cancelSeat(String rideId, CancelAccount cancelAccount) {
		logger.info("TaskSender.cancelSeat: RabbitMQ cancel message send to accounting and offer-service");
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.FREE_RIDE_KEY, new FreeRide(rideId));
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CANCEL_ACCOUNTING_KEY, cancelAccount);
	}

	/**
	 * Notifies the accounting and offer-service that a seat has been accepted
	 *
	 * @param rideId        - The id of the ride to which the seat belongs
	 * @param createAccount - The Rabbitmq message object that contains all the information for the accounting service
	 * @param currentSeats  - The amount of seats currently assigned to this trip
	 */
	public void acceptSeat(String rideId, CreateAccount createAccount, int currentSeats) {
		logger.info("TaskSender.acceptSeat: RabbitMQ accept message send to accounting and offer-service");
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.OCCUPY_RIDE_KEY, new OccupyRide(rideId, currentSeats));
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CREATE_ACCOUNTING_KEY, createAccount);
	}

}

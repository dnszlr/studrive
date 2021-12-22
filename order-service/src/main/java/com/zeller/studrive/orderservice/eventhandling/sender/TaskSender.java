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

	Logger logger = LoggerFactory.getLogger(TaskSender.class);

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange directExchange;

	public void cancelSeat(String rideId, CancelAccount cancelAccount) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.FREE_RIDE_KEY, new FreeRide(rideId));
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CANCEL_ACCOUNTING_KEY, cancelAccount);
	}

	public void acceptSeat(String rideId, CreateAccount createAccount, int currentSeats) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.OCCUPY_RIDE_KEY, new OccupyRide(rideId, currentSeats));
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CREATE_ACCOUNTING_KEY, createAccount);
	}

}

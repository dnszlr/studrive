package com.zeller.studrive.offerservice.eventhandling.sender;

import com.zeller.studrive.offerservicemq.basic.RabbitMQConstant;
import com.zeller.studrive.orderservicemq.basic.Operation;
import com.zeller.studrive.orderservicemq.eventmodel.UpdateSeats;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class TaskSender {

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange directExchange;

	public void cancelRide(String rideId) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CANCEL_SEATS_KEY, new UpdateSeats(rideId, Operation.CANCEL));
	}

	public void closeRide(String rideId) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CLOSE_SEATS_KEY, new UpdateSeats(rideId, Operation.CLOSE));
	}
}

package com.zeller.studrive.offerservice.eventhandling.sender;

import com.zeller.studrive.offerservicemq.basic.RabbitMQConstant;
import com.zeller.studrive.orderservicemq.eventmodel.RideCanceled;
import com.zeller.studrive.orderservicemq.eventmodel.RideClosed;
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

	public void cancelSeats(RideCanceled rideCanceled) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CANCEL_RIDE_KEY, rideCanceled);
	}

	public void closeSeats(RideClosed rideClosed) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CLOSE_RIDE_KEY, rideClosed);
	}
}

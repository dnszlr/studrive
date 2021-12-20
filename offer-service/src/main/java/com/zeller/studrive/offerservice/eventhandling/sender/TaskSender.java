package com.zeller.studrive.offerservice.eventhandling.sender;

import com.zeller.studrive.rabbitmqdata.OfferServiceConstant;
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

	public void cancelSeats(String id) {
		template.convertAndSend(directExchange.getName(), OfferServiceConstant.OFFER_TO_ORDER_KEY, id);
	}

	public void closeSeats(String id) {
		template.convertAndSend(directExchange.getName(), OfferServiceConstant.OFFER_TO_ORDER_KEY, id);
	}
}

package com.zeller.studrive.orderservice.eventhandling.sender;

import com.zeller.studrive.rabbitmqdata.OrderServiceConstant;
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

	public void cancelOperation(String id) {
		template.convertAndSend(directExchange.getName(), OrderServiceConstant.ORDER_TO_ACCOUNTING_KEY, id);
		template.convertAndSend(directExchange.getName(), OrderServiceConstant.ORDER_TO_OFFER_KEY, id);
	}

	public void acceptOperation(String id) {
		template.convertAndSend(directExchange.getName(), OrderServiceConstant.ORDER_TO_ACCOUNTING_KEY, id);
		template.convertAndSend(directExchange.getName(), OrderServiceConstant.ORDER_TO_OFFER_KEY, id);
	}

	public void declineOperation(String id) {
		template.convertAndSend(directExchange.getName(), OrderServiceConstant.ORDER_TO_OFFER_KEY, id);
	}
}

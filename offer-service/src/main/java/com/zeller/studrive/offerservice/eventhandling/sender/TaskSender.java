package com.zeller.studrive.offerservice.eventhandling.sender;

import com.zeller.studrive.offerservice.basic.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

public class TaskSender {

	Logger logger = LoggerFactory.getLogger(TaskSender.class);

	@Autowired
	private RabbitTemplate template;

	@Autowired
	private DirectExchange directExchange;

	@Scheduled(fixedDelay = 1000, initialDelay = 500)
	public void send() {
		String message = "Hallo von offer.service";
		template.convertAndSend(directExchange.getName(), "offerToOrder.key", message);
		logger.info("Message send: " + message);
	}
}

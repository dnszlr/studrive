package com.zeller.studrive.offerservice.eventhandling.receiver;

import com.zeller.studrive.offerservice.basic.Constant;
import com.zeller.studrive.rabbitmqdata.OrderServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class TaskReceiver {

	Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@RabbitListener(queues = OrderServiceConstant.ORDER_TO_OFFER_QUEUE)
	public void receive(String in) {
		receive(in, 1);
	}

	public void receive(String in, int receiver) {
		logger.info("Received message from order: " + in);
	}
}

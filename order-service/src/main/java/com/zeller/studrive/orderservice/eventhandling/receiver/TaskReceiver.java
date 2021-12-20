package com.zeller.studrive.orderservice.eventhandling.receiver;

import com.zeller.studrive.orderservice.basic.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class TaskReceiver {

	Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@RabbitListener(queues = "order.offer.queue")
	public void receiveOfferMessage(String in) throws InterruptedException {
		receive(in, 1);
	}

	public void receive(String in, int receiver) {
		logger.info("Received Message from offer: " + in);
	}
}

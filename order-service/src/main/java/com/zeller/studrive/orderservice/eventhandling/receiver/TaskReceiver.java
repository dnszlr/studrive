package com.zeller.studrive.orderservice.eventhandling.receiver;

import com.zeller.studrive.offerservicemq.basic.RabbitMQConstant;
import com.zeller.studrive.orderservicemq.eventmodel.RideCanceled;
import com.zeller.studrive.orderservicemq.eventmodel.RideClosed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class TaskReceiver {

	Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@RabbitListener(queues = RabbitMQConstant.CANCEL_RIDE_QUEUE)
	public void receive1(String in) throws InterruptedException {
		receive("ride canceled", 1);
	}

	@RabbitListener(queues = RabbitMQConstant.CLOSE_RIDE_QUEUE)
	public void receive2(String in) throws InterruptedException {
		receive("ride closed", 1);
	}

	public void receive(String in, int receiver) {
		logger.info("Received Message from offer: " + in);
	}
}

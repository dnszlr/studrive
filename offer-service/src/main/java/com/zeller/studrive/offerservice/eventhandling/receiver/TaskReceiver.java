package com.zeller.studrive.offerservice.eventhandling.receiver;

import com.zeller.studrive.offerservicemq.eventmodel.SeatAccepted;
import com.zeller.studrive.offerservicemq.eventmodel.SeatCanceled;
import com.zeller.studrive.orderservicemq.basic.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class TaskReceiver {

	Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@RabbitListener(queues = RabbitMQConstant.ACCEPT_SEAT_QUEUE)
	public void receive1(String in) {
		receive("seat accepted", 1);
	}

	@RabbitListener(queues = RabbitMQConstant.CANCEL_SEAT_QUEUE)
	public void receive2(String in) {
		receive("seat canceled", 1);
	}

	public void receive(String in, int receiver) {
		logger.info("Received message from order: " + in);
	}
}

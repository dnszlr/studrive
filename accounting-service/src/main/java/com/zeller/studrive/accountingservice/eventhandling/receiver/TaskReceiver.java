package com.zeller.studrive.accountingservice.eventhandling.receiver;

import com.zeller.studrive.accoutingservicemq.eventmodel.AccountCanceled;
import com.zeller.studrive.accoutingservicemq.eventmodel.AccountCreated;
import com.zeller.studrive.orderservicemq.basic.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class TaskReceiver {

	Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@RabbitListener(queues = RabbitMQConstant.CREATE_ACCOUNTING_QUEUE)
	public void createAccount(String in) {
		receive("account created", 1);
	}

	@RabbitListener(queues = RabbitMQConstant.CANCEL_ACCOUNTING_QUEUE)
	public void cancelAccount(String in) {
		receive("account canceled", 1);
	}

	public void receive(String in, int receiver) {
		logger.info("Received message from order: " + in);
	}

}

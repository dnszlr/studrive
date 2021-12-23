package com.zeller.studrive.accountingservice.eventhandling.receiver;

import com.zeller.studrive.accountingservice.model.Accounting;
import com.zeller.studrive.accountingservice.model.AccountingStatus;
import com.zeller.studrive.accountingservice.service.AccountingService;
import com.zeller.studrive.accoutingservicemq.eventmodel.CancelAccount;
import com.zeller.studrive.accoutingservicemq.eventmodel.CreateAccount;
import com.zeller.studrive.orderservicemq.basic.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class TaskReceiver {

	Logger logger = LoggerFactory.getLogger(TaskReceiver.class);

	@Autowired
	private AccountingService accountingService;

	/**
	 * Subscribes to the RabbitMQ query through which a message is transmitted from the order-service if a seat is canceled.
	 *
	 * @param cancelAccount- RabbitMQ message object that contains all the required information.
	 */
	@RabbitListener(queues = RabbitMQConstant.CANCEL_ACCOUNTING_QUEUE)
	public void cancelAccount(CancelAccount cancelAccount) {
		Optional<Accounting> accountingTemp = accountingService.findBySeat(cancelAccount.getSeatId());
		if(accountingTemp.isPresent()) {
			Accounting accounting = accountingTemp.get();
			accounting.setAccountingStatus(AccountingStatus.CANCELED);
			accountingService.save(accounting);
		} else {
			logger.info("TaskReceiver.cancelAccount: No account was found.");
		}
	}

	/**
	 * Subscribes to the RabbitMQ query through which a message is transmitted from the order-service if a seat is accepted.
	 *
	 * @param createAccount - RabbitMQ message object that contains all the required information.
	 */
	@RabbitListener(queues = RabbitMQConstant.CREATE_ACCOUNTING_QUEUE)
	public void createAccount(CreateAccount createAccount) {
		Accounting accounting = new Accounting(createAccount);
		accountingService.save(accounting);
		logger.info("TaskReceiver.createAccount: New account created: " + accounting.getId());
	}
}

package com.zeller.studrive.orderservice.eventhandling.sender;

import com.zeller.studrive.accoutingservicemq.eventmodel.AccountCanceled;
import com.zeller.studrive.accoutingservicemq.eventmodel.AccountCreated;
import com.zeller.studrive.offerservicemq.eventmodel.SeatAccepted;
import com.zeller.studrive.offerservicemq.eventmodel.SeatCanceled;
import com.zeller.studrive.orderservicemq.basic.RabbitMQConstant;
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

	public void cancelSeat(SeatCanceled seatCanceled, AccountCanceled accountCanceled) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CANCEL_SEAT_KEY, seatCanceled);
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CANCEL_ACCOUNTING_KEY, accountCanceled);
	}

	public void acceptSeat(SeatAccepted seatAccepted, AccountCreated accountCreated) {
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.ACCEPT_SEAT_KEY, seatAccepted);
		template.convertAndSend(directExchange.getName(), RabbitMQConstant.CREATE_ACCOUNTING_KEY, accountCreated);
	}

}

package com.zeller.studrive.orderservice.eventhandling;

import com.zeller.studrive.orderservice.eventhandling.receiver.TaskReceiver;
import com.zeller.studrive.orderservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.orderservicemq.basic.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange(RabbitMQConstant.ORDER_DIRECT);
	}

	private static class ReceiverConfig {

		@Bean
		public Queue acceptSeatQueue() {
			return new Queue(RabbitMQConstant.ACCEPT_SEAT_QUEUE);
		}

		@Bean
		public Binding bindingAcceptSeat(DirectExchange direct,
									Queue acceptSeatQueue) {
			return BindingBuilder.bind(acceptSeatQueue)
					.to(direct)
					.with(RabbitMQConstant.ACCEPT_SEAT_KEY);
		}

		@Bean
		public Queue cancelSeatQueue() {
			return new Queue(RabbitMQConstant.CANCEL_SEAT_QUEUE);
		}

		@Bean
		public Binding bindingCancelSeat(DirectExchange direct,
										 Queue cancelSeatQueue) {
			return BindingBuilder.bind(cancelSeatQueue)
					.to(direct)
					.with(RabbitMQConstant.CANCEL_SEAT_KEY);
		}


		@Bean
		public Queue createAccountingQueue() {
			return new Queue(RabbitMQConstant.CREATE_ACCOUNTING_QUEUE);
		}

		@Bean
		public Binding bindingCreateAccounting(DirectExchange direct,
										 Queue createAccountingQueue) {
			return BindingBuilder.bind(createAccountingQueue)
					.to(direct)
					.with(RabbitMQConstant.CREATE_ACCOUNTING_KEY);
		}

		@Bean
		public Queue cancelAccountingQueue() {
			return new Queue(RabbitMQConstant.CANCEL_ACCOUNTING_QUEUE);
		}

		@Bean
		public Binding bindingCancelAccounting(DirectExchange direct,
										 Queue cancelAccountingQueue) {
			return BindingBuilder.bind(cancelAccountingQueue)
					.to(direct)
					.with(RabbitMQConstant.CANCEL_ACCOUNTING_KEY);
		}

		@Bean
		public TaskReceiver receiver() {
			return new TaskReceiver();
		}
	}

	@Bean
	public TaskSender sender() {
		return new TaskSender();
	}

}

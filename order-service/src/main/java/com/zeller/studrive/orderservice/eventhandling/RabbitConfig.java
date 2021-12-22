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
		public Queue updateRideQueue() {
			return new Queue(RabbitMQConstant.OCCUPY_RIDE_QUEUE);
		}

		@Bean
		public Binding bindingUpdateRide(DirectExchange direct,
										 Queue updateRideQueue) {
			return BindingBuilder.bind(updateRideQueue)
					.to(direct)
					.with(RabbitMQConstant.OCCUPY_RIDE_KEY);
		}

		@Bean
		public Queue availableRideQueue() {
			return new Queue(RabbitMQConstant.FREE_RIDE_QUEUE);
		}

		@Bean
		public Binding bindingAvailableRide(DirectExchange direct,
											Queue availableRideQueue) {
			return BindingBuilder.bind(availableRideQueue)
					.to(direct)
					.with(RabbitMQConstant.FREE_RIDE_KEY);
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

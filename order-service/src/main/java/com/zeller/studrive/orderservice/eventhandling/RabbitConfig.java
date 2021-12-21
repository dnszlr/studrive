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
		public Queue offerQueue() {
			return new Queue(RabbitMQConstant.ORDER_TO_OFFER_QUEUE);
		}

		@Bean
		public Binding bindingOffer(DirectExchange direct,
									Queue offerQueue) {
			return BindingBuilder.bind(offerQueue)
					.to(direct)
					.with(RabbitMQConstant.ORDER_TO_OFFER_KEY);
		}

		@Bean
		public Queue accountingQueue() {
			return new Queue(RabbitMQConstant.ORDER_TO_ACCOUNTING_QUEUE);
		}

		@Bean
		public Binding bindingAccounting(DirectExchange direct,
										 Queue accountingQueue) {
			return BindingBuilder.bind(accountingQueue)
					.to(direct)
					.with(RabbitMQConstant.ORDER_TO_ACCOUNTING_KEY);
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

package com.zeller.studrive.offerservice.eventhandling;

import com.zeller.studrive.offerservice.eventhandling.receiver.TaskReceiver;
import com.zeller.studrive.offerservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.offerservicemq.basic.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange(RabbitMQConstant.OFFER_DIRECT);
	}

	private static class ReceiverConfig {

		@Bean
		public Queue orderQueue() {
			return new Queue(RabbitMQConstant.OFFER_TO_ORDER_QUEUE);
		}

		@Bean
		public Binding bindingOffer(DirectExchange direct,
									Queue orderQueue) {
			return BindingBuilder.bind(orderQueue)
					.to(direct)
					.with(RabbitMQConstant.OFFER_TO_ORDER_KEY);
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

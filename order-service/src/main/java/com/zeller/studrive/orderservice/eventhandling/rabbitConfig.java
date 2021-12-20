package com.zeller.studrive.orderservice.eventhandling;

import com.zeller.studrive.orderservice.basic.Constant;
import com.zeller.studrive.orderservice.eventhandling.receiver.TaskReceiver;
import com.zeller.studrive.orderservice.eventhandling.sender.TaskSender;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class rabbitConfig {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange(Constant.DIRECT);
	}

	private static class ReceiverConfig {

		@Bean
		public Queue offerQueue() {
			return new Queue(Constant.OFFER_ORDER_QUEUE);
		}

		@Bean
		public Binding bindingOffer(DirectExchange direct,
									Queue offerQueue) {
			return BindingBuilder.bind(offerQueue)
					.to(direct)
					.with(Constant.FROM_OFFER_KEY);
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

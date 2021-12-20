package com.zeller.studrive.offerservice.eventhandling;

import com.zeller.studrive.offerservice.basic.Constant;
import com.zeller.studrive.offerservice.eventhandling.receiver.TaskReceiver;
import com.zeller.studrive.offerservice.eventhandling.sender.TaskSender;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
public class rabbitConfig {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange(Constant.DIRECT);
	}

	private static class ReceiverConfig {

		@Bean
		public Queue orderQueue() {
			return new Queue(Constant.ORDER_OFFER_QUEUE);
		}

		@Bean
		public Binding bindingOffer(DirectExchange direct,
									Queue orderQueue) {
			return BindingBuilder.bind(orderQueue)
					.to(direct)
					.with(Constant.FROM_ORDER_KEY);
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

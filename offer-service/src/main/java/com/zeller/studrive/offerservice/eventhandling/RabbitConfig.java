package com.zeller.studrive.offerservice.eventhandling;

import com.zeller.studrive.offerservice.basic.Constant;
import com.zeller.studrive.offerservice.eventhandling.receiver.TaskReceiver;
import com.zeller.studrive.offerservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.rabbitmqdata.OfferServiceConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange(OfferServiceConstant.DIRECT);
	}

	private static class ReceiverConfig {

		@Bean
		public Queue orderQueue() {
			return new Queue(OfferServiceConstant.OFFER_TO_ORDER_QUEUE);
		}

		@Bean
		public Binding bindingOffer(DirectExchange direct,
									Queue orderQueue) {
			return BindingBuilder.bind(orderQueue)
					.to(direct)
					.with(OfferServiceConstant.OFFER_TO_ORDER_KEY);
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

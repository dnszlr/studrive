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
		public Queue cancelSeatsQueue() {
			return new Queue(RabbitMQConstant.CANCEL_SEATS_QUEUE);
		}

		@Bean
		public Binding bindingCancelSeats(DirectExchange direct,
									Queue cancelSeatsQueue) {
			return BindingBuilder.bind(cancelSeatsQueue)
					.to(direct)
					.with(RabbitMQConstant.CANCEL_SEATS_KEY);
		}

		@Bean
		public Queue closeSeatsQueue() {
			return new Queue(RabbitMQConstant.CLOSE_SEATS_QUEUE);
		}

		@Bean
		public Binding bindingCloseSeats(DirectExchange direct,
									Queue closeSeatsQueue) {
			return BindingBuilder.bind(closeSeatsQueue)
					.to(direct)
					.with(RabbitMQConstant.CLOSE_SEATS_KEY);
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

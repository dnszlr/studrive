package com.zeller.studrive.offerservice.eventhandling;

import com.zeller.studrive.offerservice.eventhandling.receiver.TaskReceiver;
import com.zeller.studrive.offerservice.eventhandling.sender.TaskSender;
import com.zeller.studrive.offerservicemq.basic.RabbitMQConstant;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class configures the queues and keys for the communication via RabbitMQ. For this purpose the respective queues are defined here
 * on which the class offer and with which key this happens.
 */
@Configuration
public class RabbitConfig {

	@Bean
	public DirectExchange direct() {
		return new DirectExchange(RabbitMQConstant.OFFER_DIRECT);
	}

	private static class ReceiverConfig {

		// Queue
		@Bean
		public Queue cancelSeatsQueue() {
			return new Queue(RabbitMQConstant.SEATS_CANCEL_QUEUE);
		}

		// Key for Queue
		@Bean
		public Binding bindingCancelSeats(DirectExchange direct,
										  Queue cancelSeatsQueue) {
			return BindingBuilder.bind(cancelSeatsQueue)
					.to(direct)
					.with(RabbitMQConstant.SEATS_CANCEL_KEY);
		}

		// Queue
		@Bean
		public Queue closeSeatsQueue() {
			return new Queue(RabbitMQConstant.SEATS_CLOSE_QUEUE);
		}

		// Key for Queue
		@Bean
		public Binding bindingCloseSeats(DirectExchange direct,
										 Queue closeSeatsQueue) {
			return BindingBuilder.bind(closeSeatsQueue)
					.to(direct)
					.with(RabbitMQConstant.SEATS_CLOSE_KEY);
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

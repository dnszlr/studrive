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
		public Queue cancelRideQueue() {
			return new Queue(RabbitMQConstant.CANCEL_RIDE_QUEUE);
		}

		@Bean
		public Binding bindingCancelRide(DirectExchange direct,
									Queue cancelRideQueue) {
			return BindingBuilder.bind(cancelRideQueue)
					.to(direct)
					.with(RabbitMQConstant.CANCEL_RIDE_KEY);
		}

		@Bean
		public Queue closeRideQueue() {
			return new Queue(RabbitMQConstant.CLOSE_RIDE_QUEUE);
		}

		@Bean
		public Binding bindingCloseRide(DirectExchange direct,
									Queue closeRideQueue) {
			return BindingBuilder.bind(closeRideQueue)
					.to(direct)
					.with(RabbitMQConstant.CLOSE_RIDE_KEY);
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

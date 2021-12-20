package com.zeller.studrive.accountingservice.eventhandling;

import com.zeller.studrive.accountingservice.eventhandling.receiver.TaskReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	@Bean
	public TaskReceiver receiver() {
		return new TaskReceiver();
	}
}

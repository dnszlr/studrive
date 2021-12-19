package com.zeller.studrive.orderservice.configuration;

import com.zeller.studrive.orderservice.basic.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean("userClient")
	public WebClient user() {
		return WebClient.create(Constant.USERCLIENT);
	}

	@Bean("offerClient")
	public WebClient offer() {
		return WebClient.create(Constant.OFFERCLIENT);
	}
}

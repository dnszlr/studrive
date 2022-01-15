package com.zeller.studrive.orderservice.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class configures the API endpoints to the user and offer-service. Using these endpoints, http requests can later be sent to the
 * respective service via the web client.
 */
@Configuration
public class WebClientConfig {

	@Value("${userservice.url}")
	private String userServiceUrl;

	@Value("${offerservice.url}")
	private String offerServiceUrl;

	@Bean("userClient")
	public WebClient user() {
		return WebClient.create(userServiceUrl);
	}

	@Bean("offerClient")
	public WebClient offer() {
		return WebClient.create(offerServiceUrl);
	}
}

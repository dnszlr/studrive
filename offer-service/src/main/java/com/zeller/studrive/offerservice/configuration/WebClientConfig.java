package com.zeller.studrive.offerservice.configuration;

import com.zeller.studrive.offerservice.basic.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class configures the API endpoints to the external mapbox client. Using these endpoints, http requests can later be sent to the
 * respective service via the web client.
 */
@Configuration
public class WebClientConfig {

	@Bean
	public WebClient mapbox() {
		return WebClient.create(Constant.MAPBOXAPI);
	}
}

package com.zeller.studrive.offerservice.configuration;

import com.zeller.studrive.offerservice.basic.Constant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient mapbox() {
		return WebClient.create(Constant.MAPBOXAPI);
	}
}

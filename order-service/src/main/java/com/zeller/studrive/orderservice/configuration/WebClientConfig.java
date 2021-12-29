package com.zeller.studrive.orderservice.configuration;

import com.zeller.studrive.orderservice.basic.Constant;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * This class configures the API endpoints to the user and offer-service. Using these endpoints, http requests can later be sent to the
 * respective service via the web client.
 */
@Configuration
public class WebClientConfig {

	@Bean("userClient")
	public WebClient user() {
		return WebClient.create(Constant.HTTP_PREFIX + Constant.USER_CLIENT);
	}

	@Bean("offerClient")
	public WebClient offer() {
		return WebClient.create(Constant.HTTP_PREFIX + Constant.OFFER_CLIENT);
	}
}

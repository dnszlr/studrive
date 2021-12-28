package com.zeller.studrive.orderservice.configuration;

import com.zeller.studrive.orderservice.basic.Constant;
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

	@Autowired
	private Environment environment;

	Logger logger = LoggerFactory.getLogger(WebClientConfig.class);

	@Bean("userClient")
	public WebClient user() {
		return WebClient.create(getDynamicHost(Constant.USERCLIENT));
	}

	@Bean("offerClient")
	public WebClient offer() {
		return WebClient.create(getDynamicHost(Constant.OFFERCLIENT));
	}

	public String getDynamicHost(String path) {
		String[] activeProfile = environment.getActiveProfiles();
		String client = path.equals(Constant.USERCLIENT) ? Constant.USER_SERVICE :  Constant.OFFER_SERVICE;
		String host = activeProfile[0].equals(Constant.DOCKER_PROFILE) ? client : Constant.LOCALHOST;
		logger.info("Running on host: " + host);
		return Constant.HTTP_PREFIX + host + path;
	}
}

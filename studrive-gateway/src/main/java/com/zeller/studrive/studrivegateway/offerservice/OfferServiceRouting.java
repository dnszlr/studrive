package com.zeller.studrive.studrivegateway.offerservice;

import com.zeller.studrive.studrivegateway.basic.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferServiceRouting {

	@Value("${offerservice.url}")
	private String offerserviceUrl;

	@Bean
	public RouteLocator offerRouting(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(route -> route.path("/v1/rides/**").and().method(Constant.GET).uri(offerserviceUrl))
				.route(route -> route.path("/v1/rides").and().method(Constant.POST).uri(offerserviceUrl))
				.route(route -> route.path("/v1/rides/**").and().method(Constant.PUT).uri(offerserviceUrl))
				.build();
	}

}

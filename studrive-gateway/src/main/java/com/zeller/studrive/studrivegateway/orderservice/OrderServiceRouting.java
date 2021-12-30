package com.zeller.studrive.studrivegateway.orderservice;

import com.zeller.studrive.studrivegateway.basic.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceRouting {

	@Value("${orderservice.url}")
	private String orderserviceUrl;

	@Bean
	public RouteLocator orderRouting(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(route -> route.path("/v1/seats/**").and().method(Constant.GET).uri(orderserviceUrl))
				.route(route -> route.path("/v1/seats").and().method(Constant.POST).uri(orderserviceUrl))
				.route(route -> route.path("/v1/seats/**").and().method(Constant.PUT).uri(orderserviceUrl))
				.build();
	}
}

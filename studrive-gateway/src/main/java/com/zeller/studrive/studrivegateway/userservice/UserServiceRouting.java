package com.zeller.studrive.studrivegateway.userservice;

import com.zeller.studrive.studrivegateway.basic.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceRouting {

	@Value("${userservice.url}")
	private String userserviceUrl;

	@Bean
	public RouteLocator userRouting(RouteLocatorBuilder builder) {
		return builder.routes()
				.route(route -> route.path("/").uri(userserviceUrl))
				.route(route -> route.path("/test").uri("https://spring.io/"))
				.build();
	}
}

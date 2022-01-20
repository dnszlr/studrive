package com.zeller.studrive.studrivegateway.apiComposition.rides;

import com.zeller.studrive.studrivegateway.apiComposition.proxy.RideService;
import com.zeller.studrive.studrivegateway.apiComposition.proxy.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RideConfig {

	@Bean
	public RouterFunction<ServerResponse> findRideByIdWithDetails(RideAggregator rideAggregator) {
		return RouterFunctions.route(GET("/v1/rides/{rideId}/details"), rideAggregator::getRideComposition);
	}

	@Bean
	public RideAggregator rideAggregator(RideService rideService, UserService userService) {
		return new RideAggregator(userService, rideService);
	}

	@Bean
	public WebClient webClient() {
		return WebClient.create();
	}
}

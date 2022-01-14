package com.zeller.studrive.studrivegateway.apiComposition.rides;

import com.zeller.studrive.studrivegateway.apiComposition.model.RideRequest;
import com.zeller.studrive.studrivegateway.apiComposition.proxy.RideService;
import com.zeller.studrive.studrivegateway.apiComposition.proxy.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


public class RideAggregator {

	private final UserService userService;
	private final RideService rideService;

	public RideAggregator(UserService userService, RideService rideService) {
		this.userService = userService;
		this.rideService = rideService;
	}

	/**
	 * Calls the ride Service to get a ride by the passed id, after that the drive id gets extracted and the user service is called to get
	 * the drive data.
	 *
	 * @param serverRequest - The Request from the Server to combine two endpoints into one gateway aggregation
	 * @return The combined response from two endpoints
	 */
	public Mono<ServerResponse> getRideComposition(ServerRequest serverRequest) {
		String rideId = serverRequest.pathVariable("rideId");
		Mono<RideRequest> rideRequest = rideService.findRideById(rideId);
		return rideRequest.flatMap(request -> Mono.just(userService.findUserById(request.getDriverId()))
				.flatMap(userRequest -> {
					Mono<RideComposition> rideComposition = Mono.zip(rideRequest, userRequest).map(RideComposition::create);
					return rideComposition.flatMap(response -> ServerResponse.ok()
							.contentType(MediaType.APPLICATION_JSON)
							.body(BodyInserters.fromValue(response)));
				})).onErrorResume(Exception.class, e -> ServerResponse.notFound().build());
	}
}


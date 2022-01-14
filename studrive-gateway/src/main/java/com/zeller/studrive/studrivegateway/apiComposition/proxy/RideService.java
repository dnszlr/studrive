package com.zeller.studrive.studrivegateway.apiComposition.proxy;

import com.zeller.studrive.studrivegateway.apiComposition.model.RideRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class RideService {

	@Value("${urls.offer-service}")
	private String rideUrl;
	private final WebClient webClient;

	public RideService(WebClient webClient) {
		this.webClient = webClient;
	}

	public Mono<RideRequest> findRideById(String rideId) {
		return webClient
				.get()
				.uri(rideUrl + "/v1/rides/{rideId}", rideId)
				.retrieve()
				.bodyToMono(RideRequest.class);
	}

}

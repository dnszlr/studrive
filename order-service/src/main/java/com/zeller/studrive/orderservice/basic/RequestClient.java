package com.zeller.studrive.orderservice.basic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Service
public class RequestClient {

	private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(3);
	final Logger logger = LoggerFactory.getLogger(RequestClient.class);

	@Autowired
	@Qualifier("userClient")
	private final WebClient userClient;
	@Autowired
	@Qualifier("offerClient")
	private final WebClient offerClient;

	public RequestClient(WebClient userClient, WebClient offerClient) {
		this.userClient = userClient;
		this.offerClient = offerClient;
	}

	public boolean verifyRideSeats(String rideId) {
		String path = Constant.SLASH + rideId + Constant.SLASH + Constant.SEATS;
		logger.info("RequestClient.verifyRideSeats called path: " + path);
		return Boolean.TRUE.equals(offerClient.get().uri(path)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(boolean.class)
				.block(REQUEST_TIMEOUT));
	}

	public boolean verifyPaymentDetail(Long passengerId) {
		String path = Constant.SLASH + passengerId + Constant.SLASH + Constant.VERIFY;
		logger.info("RequestClient.verifyPaymentDetail called path: " + path);
		return Boolean.TRUE.equals(userClient.get().uri(path).accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.bodyToMono(boolean.class)
				.block(REQUEST_TIMEOUT));
	}
}

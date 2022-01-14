package com.zeller.studrive.studrivegateway.apiComposition.proxy;

import com.zeller.studrive.studrivegateway.apiComposition.model.UserRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserService {

	@Value("${urls.user-service}")
	private String userUrl;
	private final WebClient webClient;

	public UserService(WebClient webClient) {
		this.webClient = webClient;
	}

	public Mono<UserRequest> findUserById(Long userId) {
		return webClient
				.get()
				.uri(userUrl + "/v1/users/{userId}", userId)
				.retrieve()
				.bodyToMono(UserRequest.class);
	}
}

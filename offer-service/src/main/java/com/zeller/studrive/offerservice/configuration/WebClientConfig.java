package com.zeller.studrive.offerservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

	@Bean
	public WebClient apiClient() {
		return WebClient.create("https://api.mapbox.com/geocoding/v5/mapbox.places");
	}

	public static String getMapboxToken() {
		return "sk.eyJ1IjoibWthbGFzaCIsImEiOiJja3AyYWVsNm0xMjltMndsZ3FqZXhnZG11In0.G0zqmJ50IGR31LpPx82LNg";
	}
}

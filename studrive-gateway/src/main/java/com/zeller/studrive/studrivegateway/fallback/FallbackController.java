package com.zeller.studrive.studrivegateway.fallback;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FallbackController {

	@GetMapping("/circuit-fallback")
	public String orderFallback() {
		return "Sorry, that shouldn't have happened. You can see a CircuitBreaker fallback view here for " +
					"demonstration purposes.";
	}

}

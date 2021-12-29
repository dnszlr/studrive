package com.zeller.studrive.studrivegateway.orderservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceRouting {

	@Value("${orderservice.url}")
	private String orderserviceUrl;
}

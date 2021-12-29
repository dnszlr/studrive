package com.zeller.studrive.studrivegateway.offerservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OfferServiceRouting {

	@Value("${offerservice.url}")
	private String offerserviceUrl;


}

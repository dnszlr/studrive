package com.zeller.studrive.studrivegateway.userservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserServiceRouting {

	@Value("${userservice.url}")
	private String userserviceUrl;


}

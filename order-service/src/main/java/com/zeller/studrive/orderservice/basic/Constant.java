package com.zeller.studrive.orderservice.basic;

public class Constant {

	// API Endpoints of other services
	// Not necessary with service registry
	public static final String HTTP_PREFIX = "http://";
	public static final String OFFER_CLIENT =  "offer-service:8080/v1/rides";
	public static final String USER_CLIENT = "user-service:8080/v1/users";

	// Query parameters
	public static final String SLASH = "/";
	public static final String SEATS = "seats";
	public static final String VERIFY = "verify";
}

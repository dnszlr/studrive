package com.zeller.studrive.orderservice.basic;

public class Constant {

	// API Endpoints of other services
	// Not necessary with service registry
	public static final String HTTP_PREFIX = "http://";
	public static final String USER_LOCALHOST = "localhost:9001";
	public static final String OFFER_LOCALHOST = "localhost:9002";
	public static final String OFFER_SERVICE =  "offer-service:8080";
	public static final String USER_SERVICE = "user-service:8080";
	public static final String USERCLIENT = "/v1/users";
	public static final String OFFERCLIENT = "/v1/rides";
	public static final String DOCKER_PROFILE = "docker";

	// Query parameters
	public static final String SLASH = "/";
	public static final String SEATS = "seats";
	public static final String VERIFY = "verify";
}

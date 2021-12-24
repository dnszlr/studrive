package com.zeller.studrive.orderservice.basic;

public class Constant {

	// MongoConfig
	public static final String DATABASE = "order-service";

	// API Endpoints of other services
	// Not necessary with service registry
	public static final String USERCLIENT = "http://localhost:9001/v1/users";
	public static final String OFFERCLIENT = "http://localhost:9002/v1/rides";

	// Query parameters
	public static final String SLASH = "/";
	public static final String SEATS = "seats";
	public static final String VERIFY = "verify";
}

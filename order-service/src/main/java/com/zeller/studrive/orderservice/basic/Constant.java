package com.zeller.studrive.orderservice.basic;

public class Constant {

	// API Endpoints from other services
	// Not necessary with service registry
	public static final String USERCLIENT = "http://localhost:9001/v1/users";
	public static final String OFFERCLIENT = "http://localhost:9002/v1/rides";

	// Query parameters
	public static final String SLASH = "/";
	public static final String SEATS = "seats";
	public static final String VERIFY = "verify";

	// Rabbitmq
	public static final String DIRECT = "order.service";
	public static final String TO_OFFER_KEY = "order.offer.key";
	public static final String TO_ACCOUNTING_KEY = "order.accounting.key";
	public static final String FROM_OFFER_KEY = "offer.order.key";
	public static final String OFFER_ORDER_QUEUE = "offer.order.queue";
}

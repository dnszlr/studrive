package com.zeller.studrive.orderservicemq.basic;

public class RabbitMQConstant {

	// DIRECT
	public static final String ORDER_DIRECT = "order.service";

	// TO OFFER-SERVICE
	public static final String OCCUPY_RIDE_QUEUE = "occupy.ride.queue";
	public static final String OCCUPY_RIDE_KEY = "occupy.ride.key";
	public static final String FREE_RIDE_QUEUE = "free.ride.queue";
	public static final String FREE_RIDE_KEY = "free.ride.key";

	// TO ACCOUNTING-SERVICE
	public static final String CREATE_ACCOUNTING_QUEUE = "create.accounting.queue";
	public static final String CREATE_ACCOUNTING_KEY = "create.accounting.key";
	public static final String CANCEL_ACCOUNTING_QUEUE = "cancel.accounting.queue";
	public static final String CANCEL_ACCOUNTING_KEY = "cancel.accounting.key";
}

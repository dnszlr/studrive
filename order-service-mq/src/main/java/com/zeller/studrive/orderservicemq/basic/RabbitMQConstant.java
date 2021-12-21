package com.zeller.studrive.orderservicemq.basic;

public class RabbitMQConstant {

	// DIRECT
	public static final String ORDER_DIRECT = "order.service";

	// TO OFFER-SERVICE
	public static final String ACCEPT_SEAT_QUEUE = "accept.seat.queue";
	public static final String ACCEPT_SEAT_KEY = "accept.seat.key";
	public static final String CANCEL_SEAT_QUEUE = "cancel.seat.queue";
	public static final String CANCEL_SEAT_KEY = "cancel.seat.key";

	// TO ACCOUNTING-SERVICE
	public static final String CREATE_ACCOUNTING_QUEUE = "create.accounting.queue";
	public static final String CREATE_ACCOUNTING_KEY = "create.accounting.key";
	public static final String CANCEL_ACCOUNTING_QUEUE = "cancel.accounting.queue";
	public static final String CANCEL_ACCOUNTING_KEY = "cancel.accounting.key";
}

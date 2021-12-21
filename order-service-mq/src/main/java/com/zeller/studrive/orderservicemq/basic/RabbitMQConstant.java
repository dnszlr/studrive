package com.zeller.studrive.orderservicemq.basic;

public class RabbitMQConstant {

	// DIRECT
	public static final String ORDER_DIRECT = "order.service";
	// TO OFFER-SERVICE
	public static final String ORDER_TO_OFFER_QUEUE = "order.to.offer.queue";
	public static final String ORDER_TO_OFFER_KEY = "order.to.offer.key";
	// TO ACCOUNTING-SERVICE
	public static final String ORDER_TO_ACCOUNTING_QUEUE = "order.to.accounting.queue";
	public static final String ORDER_TO_ACCOUNTING_KEY = "order.to.accounting.key";
}

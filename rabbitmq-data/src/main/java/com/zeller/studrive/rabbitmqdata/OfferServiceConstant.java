package com.zeller.studrive.rabbitmqdata;

/**
 * RabbitMQ offer-service Routing Attributes (direct, queues, keys)
 */
public class OfferServiceConstant {

	public static final String DIRECT = "offer.service";

	// TO ORDER-SERVICE
	public static final String OFFER_TO_ORDER_QUEUE = "offer.to.order.queue";
	public static final String OFFER_TO_ORDER_KEY = "offer.to.order.key";
}

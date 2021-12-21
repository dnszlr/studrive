package com.zeller.studrive.orderservicemq.eventmodel;

import com.zeller.studrive.orderservicemq.basic.Operation;

public class RideClosed {
	private String rideId;
	private final Operation closed;

	public RideClosed(String rideId) {
		this.rideId = rideId;
		closed = Operation.CLOSED;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public Operation getClosed() {
		return closed;
	}
}

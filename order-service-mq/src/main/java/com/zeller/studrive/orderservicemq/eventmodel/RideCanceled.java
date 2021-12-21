package com.zeller.studrive.orderservicemq.eventmodel;

import com.zeller.studrive.orderservicemq.basic.Operation;

public class RideCanceled {
	private String rideId;
	private final Operation canceled;

	public RideCanceled(String rideId) {
		this.rideId = rideId;
		this.canceled = Operation.CANCELED;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public Operation getCanceled() {
		return canceled;
	}
}

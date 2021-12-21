package com.zeller.studrive.orderservicemq.eventmodel;

import java.io.Serializable;

public class RideCanceled implements Serializable {
	private String rideId;

	public RideCanceled(String rideId) {
		this.rideId = rideId;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
}

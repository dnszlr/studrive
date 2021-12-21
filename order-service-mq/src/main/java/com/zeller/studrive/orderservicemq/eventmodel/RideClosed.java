package com.zeller.studrive.orderservicemq.eventmodel;

import java.io.Serializable;

public class RideClosed implements Serializable {
	private String rideId;

	public RideClosed(String rideId) {
		this.rideId = rideId;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
}

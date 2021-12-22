package com.zeller.studrive.offerservicemq.eventmodel;

import java.io.Serializable;

public class FreeRide implements Serializable {

	private String rideId;

	public FreeRide(String rideId) {
		this.rideId = rideId;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
}

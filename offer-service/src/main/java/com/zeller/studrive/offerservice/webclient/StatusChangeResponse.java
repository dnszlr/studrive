package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.model.Ride;
import com.zeller.studrive.offerservice.model.RideStatus;

public class StatusChangeResponse {

	private String id;
	private RideStatus rideStatus;

	public StatusChangeResponse(Ride ride) {
		this.id = ride.getId();
		this.rideStatus = ride.getRideStatus();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public RideStatus getRideStatus() {
		return rideStatus;
	}

	public void setRideStatus(RideStatus rideStatus) {
		this.rideStatus = rideStatus;
	}
}

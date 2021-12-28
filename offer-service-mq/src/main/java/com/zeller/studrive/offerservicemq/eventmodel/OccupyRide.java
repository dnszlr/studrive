package com.zeller.studrive.offerservicemq.eventmodel;

import java.io.Serializable;

public class OccupyRide implements Serializable {

	private String rideId;
	private final int currentSeats;

	public OccupyRide(String rideId, int currentSeats) {
		this.rideId = rideId;
		this.currentSeats = currentSeats;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public int getCurrentSeats() {
		return currentSeats;
	}
}

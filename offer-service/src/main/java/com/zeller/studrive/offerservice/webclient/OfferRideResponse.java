package com.zeller.studrive.offerservice.webclient;

import com.zeller.studrive.offerservice.model.Ride;

public class OfferRideResponse {

	private String id;

	public OfferRideResponse(Ride ride) {
		this.id = ride.getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

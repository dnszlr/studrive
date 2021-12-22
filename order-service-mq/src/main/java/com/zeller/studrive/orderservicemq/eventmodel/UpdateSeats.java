package com.zeller.studrive.orderservicemq.eventmodel;

import com.zeller.studrive.orderservicemq.basic.Operation;

import java.io.Serializable;

public class UpdateSeats implements Serializable {
	private String rideId;
	private final Operation operation;

	public UpdateSeats(String rideId, Operation operation) {
		this.rideId = rideId;
		this.operation = operation;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public Operation getOperation() {
		return operation;
	}
}

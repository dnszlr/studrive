package com.zeller.studrive.accoutingservicemq.eventmodel;


import java.io.Serializable;

public class CreateAccount implements Serializable {

	private Long passengerId;
	private String seatId;
	private String rideId;

	public CreateAccount(Long passengerId, String seatId, String rideId) {
		this.passengerId = passengerId;
		this.seatId = seatId;
		this.rideId = rideId;
	}

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}
}

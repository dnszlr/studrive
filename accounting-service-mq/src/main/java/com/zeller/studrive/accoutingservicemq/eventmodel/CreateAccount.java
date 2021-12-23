package com.zeller.studrive.accoutingservicemq.eventmodel;


import java.io.Serializable;

public class CreateAccount implements Serializable {

	private Long passengerId;
	private String seatId;

	public CreateAccount(Long passengerId, String seatId) {
		this.passengerId = passengerId;
		this.seatId = seatId;
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
}

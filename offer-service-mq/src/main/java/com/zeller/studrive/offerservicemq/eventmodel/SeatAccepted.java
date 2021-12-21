package com.zeller.studrive.offerservicemq.eventmodel;

import java.io.Serializable;

public class SeatAccepted implements Serializable {

	private String seatId;

	public SeatAccepted(String seatId) {
		this.seatId = seatId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}
}

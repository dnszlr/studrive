package com.zeller.studrive.offerservicemq.eventmodel;

import com.zeller.studrive.offerservicemq.basic.Operation;

public class SeatAccepted {

	private String seatId;
	private final Operation accepted;

	public SeatAccepted(String seatId) {
		this.seatId = seatId;
		this.accepted = Operation.ACCEPTED;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public Operation getAccepted() {
		return accepted;
	}
}

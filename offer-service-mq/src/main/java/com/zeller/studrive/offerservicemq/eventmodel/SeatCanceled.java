package com.zeller.studrive.offerservicemq.eventmodel;

import com.zeller.studrive.offerservicemq.basic.Operation;

public class SeatCanceled {

	private String seatId;
	private final Operation canceled;

	public SeatCanceled(String seatId) {
		this.seatId = seatId;
		this.canceled = Operation.CANCELED;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	public Operation getCanceled() {
		return canceled;
	}
}

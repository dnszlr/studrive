package com.zeller.studrive.accoutingservicemq.eventmodel;

import java.io.Serializable;

public class CancelAccount implements Serializable {

	private String seatId;

	public CancelAccount(String seatId) {
		this.seatId = seatId;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setAccountId(String seatId) {
		this.seatId = seatId;
	}
}

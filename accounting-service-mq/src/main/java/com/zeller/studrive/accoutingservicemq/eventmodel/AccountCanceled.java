package com.zeller.studrive.accoutingservicemq.eventmodel;

import com.zeller.studrive.accoutingservicemq.basic.Operation;

public class AccountCanceled {

	private String seatId;
	private final Operation operation;

	public AccountCanceled(String seatId) {
		this.seatId = seatId;
		this.operation = Operation.CANCELED;
	}

	public String getSeatId() {
		return seatId;
	}

	public void setAccountId(String seatId) {
		this.seatId = seatId;
	}

	public Operation getOperation() {
		return operation;
	}
}

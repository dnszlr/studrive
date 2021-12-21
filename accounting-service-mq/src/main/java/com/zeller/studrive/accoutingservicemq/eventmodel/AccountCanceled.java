package com.zeller.studrive.accoutingservicemq.eventmodel;

import com.zeller.studrive.accoutingservicemq.basic.Operation;

public class AccountCanceled {

	private Long accountId;
	private final Operation operation;

	public AccountCanceled(Long accountId) {
		this.accountId = accountId;
		this.operation = Operation.CANCELED;
	}

	public Long getAccountId() {
		return accountId;
	}

	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}

	public Operation getOperation() {
		return operation;
	}
}

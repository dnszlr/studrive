package com.zeller.studrive.accountingservice.model;

public enum AccountingStatus {

	OPEN("open"),
	CANCELED("canceled"),
	PAYED("payed");

	private final String accountingStatus;

	AccountingStatus(String accountingStatus) {
		this.accountingStatus = accountingStatus;
	}

	public String getAccountingStatus() {
		return accountingStatus;
	}
}

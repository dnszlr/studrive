package com.zeller.studrive.accoutingservicemq.eventmodel;

import com.zeller.studrive.accoutingservicemq.basic.Operation;

public class AccountCreated {

	private Long passengerId;
	private String seatId;
	private String rideId;
	private String paymentDetails;
	private double amount;
	private final Operation created;

	public AccountCreated(Long passengerId, String seatId, String rideId, String paymentDetails, double amount) {
		this.passengerId = passengerId;
		this.seatId = seatId;
		this.rideId = rideId;
		this.paymentDetails = paymentDetails;
		this.amount = amount;
		this.created = Operation.CREATED;
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

	public String getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(String paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Operation getCreated() {
		return created;
	}
}

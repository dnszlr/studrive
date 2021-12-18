package com.zeller.studrive.orderservice.model;

public enum SeatStatus {

	PENDING("pending"),
	ACCEPTED("accepted"),
	DENIED("denied"),
	CANCELED("canceled"),
	RIDE_CANCELED("ride canceled"),
	RIDE_CLOSED("ride closed");

	final String seatStatus;

	SeatStatus(String seatStatus) {
		this.seatStatus = seatStatus;
	}

	public String getSeatStatus(){
		return this.seatStatus;
	}
}

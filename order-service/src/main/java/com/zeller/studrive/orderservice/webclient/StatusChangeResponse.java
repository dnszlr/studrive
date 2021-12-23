package com.zeller.studrive.orderservice.webclient;

import com.zeller.studrive.orderservice.model.Seat;
import com.zeller.studrive.orderservice.model.SeatStatus;

public class StatusChangeResponse {

	private String id;
	private SeatStatus seatStatus;

	public StatusChangeResponse(Seat seat) {
		this.id = seat.getId();
		this.seatStatus = seat.getSeatStatus();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}
}

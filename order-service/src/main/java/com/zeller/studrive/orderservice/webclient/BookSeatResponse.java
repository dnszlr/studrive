package com.zeller.studrive.orderservice.webclient;

import com.zeller.studrive.orderservice.model.Seat;

public class BookSeatResponse {

	private String id;

	public BookSeatResponse(Seat seat) {
		this.id = seat.getId();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}

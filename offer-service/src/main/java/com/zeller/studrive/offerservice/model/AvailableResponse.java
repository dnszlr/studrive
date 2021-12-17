package com.zeller.studrive.offerservice.model;

import java.time.LocalDate;

public class AvailableResponse {
	private Address start;
	private Address destination;
	private LocalDate startDate;

	public Address getStart() {
		return start;
	}

	public Address getDestination() {
		return destination;
	}

	public LocalDate getStartDate() {
		return startDate;
	}
}

package com.zeller.studrive.offerservice.model;

import java.time.LocalDate;

public class FindAvailableRequest {
	private final Address start;
	private final Address destination;
	private final LocalDate startDate;

	public FindAvailableRequest(Address start, Address destination, LocalDate startDate) {
		this.start = start;
		this.destination = destination;
		this.startDate = startDate;
	}

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

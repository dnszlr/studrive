package com.zeller.studrive.studrivegateway.apiComposition.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class RideRequest {

	private Long driverId;
	private AddressInfo start;
	private AddressInfo destination;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	private double pricePerSeat;
	private String rideStatus;

	public RideRequest(Long driverId, AddressInfo start, AddressInfo destination, LocalDateTime startDate, LocalDateTime endDate,
					   double pricePerSeat, String rideStatus) {
		this.driverId = driverId;
		this.start = start;
		this.destination = destination;
		this.startDate = startDate;
		this.endDate = endDate;
		this.pricePerSeat = pricePerSeat;
		this.rideStatus = rideStatus;
	}

	public RideRequest() {
	}

	public Long getDriverId() {
		return driverId;
	}

	public void setDriverId(Long driverId) {
		this.driverId = driverId;
	}

	public AddressInfo getStart() {
		return start;
	}

	public void setStart(AddressInfo start) {
		this.start = start;
	}

	public AddressInfo getDestination() {
		return destination;
	}

	public void setDestination(AddressInfo destination) {
		this.destination = destination;
	}

	public LocalDateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}

	public LocalDateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}

	public double getPricePerSeat() {
		return pricePerSeat;
	}

	public void setPricePerSeat(double pricePerSeat) {
		this.pricePerSeat = pricePerSeat;
	}

	public String getRideStatus() {
		return rideStatus;
	}

	public void setRideStatus(String rideStatus) {
		this.rideStatus = rideStatus;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;

		RideRequest that = (RideRequest) o;

		if(Double.compare(that.pricePerSeat, pricePerSeat) != 0)
			return false;
		if(!Objects.equals(driverId, that.driverId))
			return false;
		if(!Objects.equals(start, that.start))
			return false;
		if(!Objects.equals(destination, that.destination))
			return false;
		if(!Objects.equals(startDate, that.startDate))
			return false;
		if(!Objects.equals(endDate, that.endDate))
			return false;
		return Objects.equals(rideStatus, that.rideStatus);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = driverId != null ? driverId.hashCode() : 0;
		result = 31 * result + (start != null ? start.hashCode() : 0);
		result = 31 * result + (destination != null ? destination.hashCode() : 0);
		result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
		result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
		temp = Double.doubleToLongBits(pricePerSeat);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (rideStatus != null ? rideStatus.hashCode() : 0);
		return result;
	}
}

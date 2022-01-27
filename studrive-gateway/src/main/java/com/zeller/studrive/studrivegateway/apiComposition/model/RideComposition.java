package com.zeller.studrive.studrivegateway.apiComposition.model;

import reactor.util.function.Tuple2;

import java.util.Objects;

public class RideComposition {

	private RideRequest rideData;
	private UserRequest driverData;

	public RideComposition(RideRequest rideData, UserRequest driverData) {
		this.rideData = rideData;
		this.driverData = driverData;
	}

	public RideComposition() {

	}

	public RideRequest getRideData() {
		return rideData;
	}

	public void setRideData(RideRequest rideData) {
		this.rideData = rideData;
	}

	public UserRequest getDriverData() {
		return driverData;
	}

	public void setDriverData(UserRequest driverData) {
		this.driverData = driverData;
	}

	public static RideComposition create(Tuple2<RideRequest, UserRequest> data) {
		return new RideComposition(data.getT1(), data.getT2());
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;

		RideComposition that = (RideComposition) o;

		if(!Objects.equals(rideData, that.rideData))
			return false;
		return Objects.equals(driverData, that.driverData);
	}

	@Override
	public int hashCode() {
		int result = rideData != null ? rideData.hashCode() : 0;
		result = 31 * result + (driverData != null ? driverData.hashCode() : 0);
		return result;
	}
}

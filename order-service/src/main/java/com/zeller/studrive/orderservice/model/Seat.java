package com.zeller.studrive.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Objects;

@Document
public class Seat {

	@MongoId(value = FieldType.OBJECT_ID)
	@JsonProperty(access = JsonProperty.Access.READ_ONLY)
	private String id;
	private Long passengerId;
	private String rideId;
	private SeatStatus seatStatus;

	public Seat(Long passengerId, String rideId, SeatStatus seatStatus) {
		this.passengerId = passengerId;
		this.rideId = rideId;
		this.seatStatus = seatStatus;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	public SeatStatus getSeatStatus() {
		return seatStatus;
	}

	public void setSeatStatus(SeatStatus seatStatus) {
		this.seatStatus = seatStatus;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;

		Seat seat = (Seat) o;

		if(!Objects.equals(id, seat.id))
			return false;
		if(!Objects.equals(passengerId, seat.passengerId))
			return false;
		if(!Objects.equals(rideId, seat.rideId))
			return false;
		return seatStatus == seat.seatStatus;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (passengerId != null ? passengerId.hashCode() : 0);
		result = 31 * result + (rideId != null ? rideId.hashCode() : 0);
		result = 31 * result + (seatStatus != null ? seatStatus.hashCode() : 0);
		return result;
	}
}

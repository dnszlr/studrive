package com.zeller.studrive.offerservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Objects;

@Document
public class Ride {

    @Id
    private String id;
    private Long driverId;
    private Address start;
    private Address destination;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Car car;
    private double pricePerSeat;
    private RideStatus rideStatus;

    public Ride(Long driverId, Address start, Address destination, LocalDateTime startDate, LocalDateTime endDate, Car car, double pricePerSeat) {
        this.driverId = driverId;
        this.start = start;
        this.destination = destination;
        this.startDate = startDate;
        this.endDate = endDate;
        this.car = car;
        this.pricePerSeat = pricePerSeat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public Address getStart() {
        return start;
    }

    public void setStart(Address start) {
        this.start = start;
    }

    public Address getDestination() {
        return destination;
    }

    public void setDestination(Address destination) {
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

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public double getPricePerSeat() {
        return pricePerSeat;
    }

    public void setPricePerSeat(double pricePerSeat) {
        this.pricePerSeat = pricePerSeat;
    }

    public RideStatus getRideStatus() {
        return rideStatus;
    }

    public void setRideStatus(RideStatus rideStatus) {
        this.rideStatus = rideStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ride ride = (Ride) o;

        if (Double.compare(ride.pricePerSeat, pricePerSeat) != 0) return false;
        if (!Objects.equals(id, ride.id)) return false;
        if (!Objects.equals(driverId, ride.driverId)) return false;
        if (!Objects.equals(start, ride.start)) return false;
        if (!Objects.equals(destination, ride.destination)) return false;
        if (!Objects.equals(startDate, ride.startDate)) return false;
        if (!Objects.equals(endDate, ride.endDate)) return false;
        if (!Objects.equals(car, ride.car)) return false;
        return rideStatus == ride.rideStatus;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (driverId != null ? driverId.hashCode() : 0);
        result = 31 * result + (start != null ? start.hashCode() : 0);
        result = 31 * result + (destination != null ? destination.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (endDate != null ? endDate.hashCode() : 0);
        result = 31 * result + (car != null ? car.hashCode() : 0);
        temp = Double.doubleToLongBits(pricePerSeat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (rideStatus != null ? rideStatus.hashCode() : 0);
        return result;
    }
}

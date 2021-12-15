package com.zeller.studrive.offerservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document
public class Ride {

    @Id
    private String id;
    private String driverId;
    private Address start;
    private Address destination;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Car car;
    private double pricePerSeat;

    public Ride(String driverId, Address start, Address destination, LocalDateTime startDate, LocalDateTime endDate, Car car, double pricePerSeat) {
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

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ride ride = (Ride) o;

        if (Double.compare(ride.pricePerSeat, pricePerSeat) != 0) return false;
        if (!id.equals(ride.id)) return false;
        if (!driverId.equals(ride.driverId)) return false;
        if (!start.equals(ride.start)) return false;
        if (!destination.equals(ride.destination)) return false;
        if (!startDate.equals(ride.startDate)) return false;
        if (!endDate.equals(ride.endDate)) return false;
        return car.equals(ride.car);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + driverId.hashCode();
        result = 31 * result + start.hashCode();
        result = 31 * result + destination.hashCode();
        result = 31 * result + startDate.hashCode();
        result = 31 * result + endDate.hashCode();
        result = 31 * result + car.hashCode();
        temp = Double.doubleToLongBits(pricePerSeat);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}

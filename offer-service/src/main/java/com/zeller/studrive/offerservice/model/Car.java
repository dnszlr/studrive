package com.zeller.studrive.offerservice.model;

import org.springframework.data.mongodb.core.mapping.Document;

public class Car {

    private String brand;
    private String licencePlate;
    private int seats;

    public Car(String brand, String licencePlate, int seats) {
        this.brand = brand;
        this.licencePlate = licencePlate;
        this.seats = seats;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Car car = (Car) o;

        if (seats != car.seats) return false;
        if (brand != null ? !brand.equals(car.brand) : car.brand != null) return false;
        return licencePlate != null ? licencePlate.equals(car.licencePlate) : car.licencePlate == null;
    }

    @Override
    public int hashCode() {
        int result = brand != null ? brand.hashCode() : 0;
        result = 31 * result + (licencePlate != null ? licencePlate.hashCode() : 0);
        result = 31 * result + seats;
        return result;
    }
}

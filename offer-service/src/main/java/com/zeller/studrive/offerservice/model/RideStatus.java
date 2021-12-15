package com.zeller.studrive.offerservice.model;

public enum RideStatus {

    AVAILABLE("available"),
    OCCUPIED("occupied"),
    CANCELED("canceled"),
    CLOSED("closed");

    final String rideStatus;

    RideStatus(String rideStatus) {
        this.rideStatus = rideStatus;
    }

    public String getRideStatus() {
        return this.rideStatus;
    }

}

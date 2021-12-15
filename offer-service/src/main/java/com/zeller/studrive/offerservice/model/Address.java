package com.zeller.studrive.offerservice.model;

import java.util.Objects;

public class Address {

    private String location;
    private String postalCode;
    private String street;
    private int houseNumber;

    public Address(String location, String postalCode, String street, int houseNumber) {
        this.location = location;
        this.postalCode = postalCode;
        this.street = street;
        this.houseNumber = houseNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(int houseNumber) {
        this.houseNumber = houseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (houseNumber != address.houseNumber) return false;
        if (!Objects.equals(location, address.location)) return false;
        if (!Objects.equals(postalCode, address.postalCode)) return false;
        return Objects.equals(street, address.street);
    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + houseNumber;
        return result;
    }
}

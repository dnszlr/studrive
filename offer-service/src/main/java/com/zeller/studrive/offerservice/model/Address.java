package com.zeller.studrive.offerservice.model;

import java.util.Arrays;
import java.util.Objects;

public class Address {

	private String city;
	private String postalCode;
	private String street;
	private int houseNumber;
	private double[] coordinates;

	public Address(String city, String postalCode, String street, int houseNumber) {
		this.city = city;
		this.postalCode = postalCode;
		this.street = street;
		this.houseNumber = houseNumber;
	}

	public Address() {
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public double[] getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(double[] coordinates) {
		this.coordinates = coordinates;
	}

	public String getQueryString() {
		String uriSeparator = "+";
		return this.city + uriSeparator + this.postalCode + uriSeparator + this.street + uriSeparator + this.houseNumber + ".json";
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;

		Address address = (Address) o;

		if(houseNumber != address.houseNumber)
			return false;
		if(!Objects.equals(city, address.city))
			return false;
		if(!Objects.equals(postalCode, address.postalCode))
			return false;
		if(!Objects.equals(street, address.street))
			return false;
		return Arrays.equals(coordinates, address.coordinates);
	}

	@Override
	public int hashCode() {
		int result = city != null ? city.hashCode() : 0;
		result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
		result = 31 * result + (street != null ? street.hashCode() : 0);
		result = 31 * result + houseNumber;
		result = 31 * result + Arrays.hashCode(coordinates);
		return result;
	}
}

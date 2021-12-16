package com.zeller.studrive.offerservice.model;

import java.util.Objects;

public class Address {

	private String city;
	private String postalCode;
	private String street;
	private int houseNumber;
	private double latitude;
	private double longitude;

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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
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
		if(Double.compare(address.latitude, latitude) != 0)
			return false;
		if(Double.compare(address.longitude, longitude) != 0)
			return false;
		if(!Objects.equals(city, address.city))
			return false;
		if(!Objects.equals(postalCode, address.postalCode))
			return false;
		return Objects.equals(street, address.street);
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = city != null ? city.hashCode() : 0;
		result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
		result = 31 * result + (street != null ? street.hashCode() : 0);
		result = 31 * result + houseNumber;
		temp = Double.doubleToLongBits(latitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}
}

package com.zeller.studrive.studrivegateway.apiComposition.model;

import java.util.Objects;

public class AddressInfo {

	private String city;
	private String postalCode;
	private String street;
	private int houseNumber;

	public AddressInfo(String city, String postalCode, String street, int houseNumber) {
		this.city = city;
		this.postalCode = postalCode;
		this.street = street;
		this.houseNumber = houseNumber;
	}

	public AddressInfo() {
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

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;

		AddressInfo that = (AddressInfo) o;

		if(houseNumber != that.houseNumber)
			return false;
		if(!Objects.equals(city, that.city))
			return false;
		if(!Objects.equals(postalCode, that.postalCode))
			return false;
		return Objects.equals(street, that.street);
	}

	@Override
	public int hashCode() {
		int result = city != null ? city.hashCode() : 0;
		result = 31 * result + (postalCode != null ? postalCode.hashCode() : 0);
		result = 31 * result + (street != null ? street.hashCode() : 0);
		result = 31 * result + houseNumber;
		return result;
	}
}

package com.zeller.studrive.studrivegateway.apiComposition.model;

import java.util.Objects;

public class UserRequest {

	private String university;
	private String firstName;
	private String lastName;
	private String email;

	public UserRequest(String university, String firstName, String lastName, String email) {
		this.university = university;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public UserRequest() {
	}

	public String getUniversity() {
		return university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;

		UserRequest that = (UserRequest) o;

		if(!Objects.equals(university, that.university))
			return false;
		if(!Objects.equals(firstName, that.firstName))
			return false;
		if(!Objects.equals(lastName, that.lastName))
			return false;
		return Objects.equals(email, that.email);
	}

	@Override
	public int hashCode() {
		int result = university != null ? university.hashCode() : 0;
		result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
		result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		return result;
	}
}

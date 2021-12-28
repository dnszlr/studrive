package com.zeller.studrive.userservice.webclient;

public class CreateUserRequest {

	private String matriculationNumber;
	private String university;
	private String firstName;
	private String lastName;
	private String email;

	public CreateUserRequest(String matriculationNumber, String university, String firstName, String lastName, String email) {
		this.matriculationNumber = matriculationNumber;
		this.university = university;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public String getMatriculationNumber() {
		return matriculationNumber;
	}

	public void setMatriculationNumber(String matriculationNumber) {
		this.matriculationNumber = matriculationNumber;
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
}

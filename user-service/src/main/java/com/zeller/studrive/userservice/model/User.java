package com.zeller.studrive.userservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zeller.studrive.userservice.webclient.CreateUserRequest;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "userservice")
public class User {

    private Long id;
    private String matriculationNumber;
    private String university;
    private String firstName;
    private String lastName;
    private String email;
    private PaymentDetails paymentDetails;

    /**
     * Don't remove hibernate empty constructor
     */
    public User() {
    }

    public User(CreateUserRequest createUserRequest) {
        this.matriculationNumber = createUserRequest.getMatriculationNumber();
        this.university = createUserRequest.getUniversity();
        this.firstName = createUserRequest.getFirstName();
        this.lastName = createUserRequest.getLastName();
        this.email = createUserRequest.getEmail();
    }



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "matriculationNumber", nullable = false)
    public String getMatriculationNumber() {
        return matriculationNumber;
    }

    public void setMatriculationNumber(String matriculationNumber) {
        this.matriculationNumber = matriculationNumber;
    }

    @Column(name = "university", nullable = false)
    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    @Column(name = "firstName", nullable = false)
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column(name = "lastName", nullable = false)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column(name = "email", nullable = false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "paymentDetailId")
    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentDetails(PaymentDetails paymentDetails) {
        this.paymentDetails = paymentDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (!matriculationNumber.equals(user.matriculationNumber)) return false;
        if (!university.equals(user.university)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!email.equals(user.email)) return false;
        return Objects.equals(paymentDetails, user.paymentDetails);
    }

    @Override
    public int hashCode() {
        int result = matriculationNumber.hashCode();
        result = 31 * result + university.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (paymentDetails != null ? paymentDetails.hashCode() : 0);
        return result;
    }
}

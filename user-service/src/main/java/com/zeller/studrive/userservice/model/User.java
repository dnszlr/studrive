package com.zeller.studrive.userservice.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "users", schema = "userservice")
public class User {

    /**
     * Don't remove hibernate empty constructor
     */
    public User() {
    }

    private Long id;
    private String matriculationNr;
    private String university;
    private String firstName;
    private String lastName;
    private String email;
    private PaymentDetails paymentDetails;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "matriculationNr", nullable = false)
    public String getMatriculationNr() {
        return matriculationNr;
    }

    public void setMatriculationNr(String matriculationNr) {
        this.matriculationNr = matriculationNr;
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

        if (!matriculationNr.equals(user.matriculationNr)) return false;
        if (!university.equals(user.university)) return false;
        if (!firstName.equals(user.firstName)) return false;
        if (!lastName.equals(user.lastName)) return false;
        if (!email.equals(user.email)) return false;
        return Objects.equals(paymentDetails, user.paymentDetails);
    }

    @Override
    public int hashCode() {
        int result = matriculationNr.hashCode();
        result = 31 * result + university.hashCode();
        result = 31 * result + firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + (paymentDetails != null ? paymentDetails.hashCode() : 0);
        return result;
    }
}

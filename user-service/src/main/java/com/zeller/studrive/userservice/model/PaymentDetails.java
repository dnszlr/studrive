package com.zeller.studrive.userservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "paymentDetails", schema = "userservice")
public class PaymentDetails {

    private Long id;
    private String countryCode;
    private int checkDigit;
    private int bankCode;
    private int accountNumber;

    /**
     * Don't remove hibernate empty constructor
     */
    public PaymentDetails() {

    }

    public PaymentDetails(String countryCode, int checkDigit, int bankCode, int accountNumber) {
        this.countryCode = countryCode;
        this.checkDigit = checkDigit;
        this.bankCode = bankCode;
        this.accountNumber = accountNumber;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public Long getId() {
        return id;
    }

    @Column(name = "countryCode", nullable = false)
    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    @Column(name = "checkDigit", nullable = false)
    public int getCheckDigit() {
        return checkDigit;
    }

    public void setCheckDigit(int checkDigit) {
        this.checkDigit = checkDigit;
    }

    @Column(name = "bankCode", nullable = false)
    public int getBankCode() {
        return bankCode;
    }

    public void setBankCode(int bankCode) {
        this.bankCode = bankCode;
    }

    @Column(name = "accountNumber", nullable = false)
    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * Compares all fields except id for equality
     * @param that - the payment detail to compare
     * @return boolean true if equals, false if not
     */
    public boolean attributeEquals(PaymentDetails that) {
        if (checkDigit != that.checkDigit) return false;
        if (bankCode != that.bankCode) return false;
        if (accountNumber != that.accountNumber) return false;
        return countryCode.equals(that.countryCode);
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null || getClass() != o.getClass())
            return false;

        PaymentDetails that = (PaymentDetails) o;

        if(checkDigit != that.checkDigit)
            return false;
        if(bankCode != that.bankCode)
            return false;
        if(accountNumber != that.accountNumber)
            return false;
        if(!Objects.equals(id, that.id))
            return false;
        return Objects.equals(countryCode, that.countryCode);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + checkDigit;
        result = 31 * result + bankCode;
        result = 31 * result + accountNumber;
        return result;
    }
}

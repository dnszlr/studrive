package com.zeller.studrive.accountingservice.model;

import com.zeller.studrive.accoutingservicemq.eventmodel.CreateAccount;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "accounting", schema = "accountingservice")
public class Accounting {

	private Long id;
	private Long passengerId;
	private String seatId;
	private String rideId;
	@Enumerated(EnumType.STRING)
	private AccountingStatus accountingStatus;

	/**
	 * Don't remove hibernate empty constructor
	 */
	public Accounting() {
	}

	public Accounting(CreateAccount createAccount) {
		this.passengerId = createAccount.getPassengerId();
		this.seatId = createAccount.getSeatId();
		this.rideId = createAccount.getRideId();
		this.accountingStatus = AccountingStatus.OPEN;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "passenger")
	public Long getPassengerId() {
		return passengerId;
	}

	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}

	@Column(name = "seat")
	public String getSeatId() {
		return seatId;
	}

	public void setSeatId(String seatId) {
		this.seatId = seatId;
	}

	@Column(name = "ride")
	public String getRideId() {
		return rideId;
	}

	public void setRideId(String rideId) {
		this.rideId = rideId;
	}

	@Column(name = "accountingStatus")
	public AccountingStatus getAccountingStatus() {
		return accountingStatus;
	}

	public void setAccountingStatus(AccountingStatus accountingStatus) {
		this.accountingStatus = accountingStatus;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o)
			return true;
		if(o == null || getClass() != o.getClass())
			return false;

		Accounting that = (Accounting) o;

		if(!Objects.equals(id, that.id))
			return false;
		if(!Objects.equals(passengerId, that.passengerId))
			return false;
		if(!Objects.equals(seatId, that.seatId))
			return false;
		if(!Objects.equals(rideId, that.rideId))
			return false;
		return accountingStatus == that.accountingStatus;
	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (passengerId != null ? passengerId.hashCode() : 0);
		result = 31 * result + (seatId != null ? seatId.hashCode() : 0);
		result = 31 * result + (rideId != null ? rideId.hashCode() : 0);
		result = 31 * result + (accountingStatus != null ? accountingStatus.hashCode() : 0);
		return result;
	}
}

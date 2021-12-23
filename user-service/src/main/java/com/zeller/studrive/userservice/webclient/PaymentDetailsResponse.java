package com.zeller.studrive.userservice.webclient;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;

public class PaymentDetailsResponse {

	Long id;
	PaymentDetails paymentDetails;

	public PaymentDetailsResponse(User user) {
		this.id = user.getId();
		this.paymentDetails = user.getPaymentDetails();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PaymentDetails getPaymentDetails() {
		return paymentDetails;
	}

	public void setPaymentDetails(PaymentDetails paymentDetails) {
		this.paymentDetails = paymentDetails;
	}
}

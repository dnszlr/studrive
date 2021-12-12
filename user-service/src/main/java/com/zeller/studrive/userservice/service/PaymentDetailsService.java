package com.zeller.studrive.userservice.service;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentDetailsService {

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    public PaymentDetails save(PaymentDetails paymentDetails) {
        return paymentDetailsRepository.save(paymentDetails);
    }
}

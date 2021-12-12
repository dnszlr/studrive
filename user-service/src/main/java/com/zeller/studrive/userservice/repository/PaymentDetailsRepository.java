package com.zeller.studrive.userservice.repository;

import com.zeller.studrive.userservice.model.PaymentDetails;
import org.springframework.data.repository.CrudRepository;

public interface PaymentDetailsRepository extends CrudRepository<PaymentDetails, Long> {
}

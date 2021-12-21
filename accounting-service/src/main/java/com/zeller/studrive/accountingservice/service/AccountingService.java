package com.zeller.studrive.accountingservice.service;

import com.zeller.studrive.accountingservice.model.Accounting;
import com.zeller.studrive.accountingservice.repository.AccountingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountingService {

	@Autowired
	private AccountingRepository accountingRepository;

	public Accounting save(Accounting accounting) {
		return accountingRepository.save(accounting);
	}

	public Optional<Accounting> findBySeat(String seatId) {
		return accountingRepository.findAccountingBySeatId(seatId);
	}

}

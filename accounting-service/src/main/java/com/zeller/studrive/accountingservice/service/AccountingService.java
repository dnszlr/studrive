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

	/**
	 * Checks if the passed accounting is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param accounting - the accounting to be saved
	 * @return the newly created accounting
	 */
	public Accounting save(Accounting accounting) {
		return accountingRepository.save(accounting);
	}

	// TODO findById maybe necessary

	/**
	 * Returns all accounts for the passed seat id
	 * @param seatId - the id of the seat from which the accounts are to be searched for
	 * @return The accounting or null
	 */
	public Optional<Accounting> findBySeat(String seatId) {
		return accountingRepository.findAccountingBySeatId(seatId);
	}

}

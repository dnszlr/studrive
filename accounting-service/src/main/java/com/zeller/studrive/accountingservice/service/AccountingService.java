package com.zeller.studrive.accountingservice.service;

import com.zeller.studrive.accountingservice.model.Accounting;
import com.zeller.studrive.accountingservice.repository.AccountingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountingService {

	private final AccountingRepository accountingRepository;

	public AccountingService(AccountingRepository accountingRepository) {
		this.accountingRepository = accountingRepository;
	}

	/**
	 * Checks if the passed accounting is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param accounting - the accounting to be saved
	 * @return the newly created accounting
	 */
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public Accounting save(Accounting accounting) {
		return accountingRepository.save(accounting);
	}

	/**
	 * Returns the account matching the passed id
	 *
	 * @param id - The id of the requested account
	 * @return The account or null
	 */
	public Optional<Accounting> findById(Long id) {
		return accountingRepository.findAccountingById(id);
	}

	/**
	 * Returns all accounts for the passed seat id
	 *
	 * @param seatId - the id of the seat from which the accounts are to be searched for
	 * @return The accounting or null
	 */
	public Optional<Accounting> findBySeat(String seatId) {
		return accountingRepository.findAccountingBySeatId(seatId);
	}

}

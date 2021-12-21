package com.zeller.studrive.accountingservice.repository;

import com.zeller.studrive.accountingservice.model.Accounting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountingRepository extends CrudRepository<Accounting, Long> {

	Optional<Accounting> findAccountingBySeatId(String seatId);
}

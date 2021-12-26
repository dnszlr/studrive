package com.zeller.studrive.accountingservice.service;

import com.zeller.studrive.accountingservice.model.Accounting;
import com.zeller.studrive.accountingservice.repository.AccountingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountingServiceTest {

	private AccountingService testService;
	@Mock
	private AccountingRepository accountingRepository;
	private Accounting accounting;

	@BeforeEach
	void setUp() {
		testService = new AccountingService(accountingRepository);
		accounting = new Accounting();
		accounting.setSeatId("100S");
		accounting.setPassengerId(1L);
	}

	@Test
	void canSaveAccountings() {
		Mockito.when(accountingRepository.save(accounting)).thenReturn(accounting);
		Accounting result = testService.save(accounting);
		verify(accountingRepository).save(accounting);
		assertThat(result).isEqualTo(accounting);
	}

	@Test
	void canFindAccountingsById() {
		Long id = 100L;
		accounting.setId(id);
		Mockito.when(accountingRepository.findAccountingById(id)).thenReturn(Optional.of(accounting));
		Optional<Accounting> result = testService.findById(id);
		verify(accountingRepository).findAccountingById(id);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(accounting);
	}

	@Test
	void canFindAccountingsBySeat() {
		String seatId = "100S";
		Mockito.when(accountingRepository.findAccountingBySeatId(seatId)).thenReturn(Optional.of(accounting));
		Optional<Accounting> result = testService.findBySeat(seatId);
		verify(accountingRepository).findAccountingBySeatId(seatId);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(accounting);
	}
}
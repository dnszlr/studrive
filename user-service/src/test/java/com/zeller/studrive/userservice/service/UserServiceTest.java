package com.zeller.studrive.userservice.service;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private UserService testService;
	@Mock
	private UserRepository userRepository;
	private User user;

	@BeforeEach
	void setUp() {
		testService = new UserService(userRepository);
		user = new User();
		user.setFirstName("Max");
		user.setLastName("Testermann");
		user.setEmail("max@testermann.de");
		user.setMatriculationNumber("123456");
		user.setUniversity("Hochschule Reutlingen");
	}

	@Test
	void canSave() {
		Mockito.when(userRepository.save(user)).thenReturn(user);
		User result = testService.save(user);
		verify(userRepository).save(user);
		assertThat(result).isEqualTo(user);
	}

	@Test
	void canGetById() {
		Long id = 100L;
		user.setId(id);
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		Optional<User> result = testService.getById(id);
		verify(userRepository).findById(id);
		assertThat(result.isPresent()).isTrue();
		assertThat(result.get()).isEqualTo(user);
	}

	@Test
	void canUpdatePaymentDetails() {
		Long id = 100L;
		user.setId(id);
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		PaymentDetails pd = new PaymentDetails("DE", 33, 12345678, 1234567890);
		Optional<User> updated = testService.updatePaymentDetails(id, pd);
		verify(userRepository).findById(id);
		verify(userRepository).save(user);
		assertThat(updated.isPresent()).isTrue();
		assertThat(updated.get().getPaymentDetails()).isEqualTo(pd);
	}

	@Test
	void canVerifyPaymentDetails() {
		Long id = 100L;
		user.setId(id);
		PaymentDetails pd = new PaymentDetails("DE", 33, 12345678, 1234567890);
		user.setPaymentDetails(pd);
		Mockito.when(userRepository.findById(id)).thenReturn(Optional.of(user));
		boolean result = testService.verifyPaymentDetails(id);
		assertThat(result).isTrue();
	}
}
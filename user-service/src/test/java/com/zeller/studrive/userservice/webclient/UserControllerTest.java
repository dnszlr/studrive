package com.zeller.studrive.userservice.webclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UserController.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;
	@MockBean
	private UserService userService;
	private User user;

	@BeforeEach
	void setUp() {
		user = new User();
		user.setId(1L);
		user.setFirstName("Max");
		user.setLastName("Testermann");
		user.setEmail("max@testermann.de");
		user.setMatriculationNumber("123456");
		user.setUniversity("Hochschule Reutlingen");
	}

	@Test
	void canCreateUser() throws Exception {
		Mockito.when(userService.save(user)).thenReturn(user);
		mockMvc.perform(post("/v1/users/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isOk());
	}

	@Test
	void cantCreateUser() throws Exception {
		Mockito.when(userService.save(user)).thenReturn(null);
		mockMvc.perform(post("/v1/users/")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(user)))
				.andExpect(status().isNotFound());
	}

	@Test
	void canFindUserById() throws Exception {
		Mockito.when(userService.getById(1L)).thenReturn(Optional.of(user));
		mockMvc.perform(get("/v1/users/{userId}", 1L))
				.andExpect(status().isOk()).andExpect(jsonPath("$.lastName", is(user.getLastName())));
	}

	@Test
	void cantFindUserById() throws Exception {
		Mockito.when(userService.getById(1L)).thenReturn(Optional.empty());
		mockMvc.perform(get("/v1/users/{userId}", 1L)).andExpect(status().isNotFound());
	}

	@Test
	void canUpdatePaymentDetails() throws Exception {
		PaymentDetails pd = new PaymentDetails("DE", 33, 12345678, 1234567890);
		user.setPaymentDetails(pd);
		Mockito.when(userService.updatePaymentDetails(1L, pd)).thenReturn(Optional.of(user));
		mockMvc.perform(put("/v1/users/{userId}/paymentDetails", 1L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pd)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.paymentDetails.bankCode", is(pd.getBankCode())));
	}

	@Test
	void cantUpdatePaymentDetails() throws Exception {
		PaymentDetails pd = new PaymentDetails("DE", 33, 12345678, 1234567890);
		user.setPaymentDetails(pd);
		Mockito.when(userService.updatePaymentDetails(2L, pd)).thenReturn(Optional.empty());
		mockMvc.perform(put("/v1/users/{userId}/paymentDetails", 2L)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(pd)))
				.andExpect(status().isNotFound());
	}

	@Test
	void canVerifyPaymentDetails() throws Exception {
		Mockito.when(userService.verifyPaymentDetails(1L)).thenReturn(true);
		mockMvc.perform(get("/v1/users/{userId}/verify", 1L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(true)));
	}

	@Test
	void cantVerifyPaymentDetails() throws Exception {
		Mockito.when(userService.verifyPaymentDetails(2L)).thenReturn(false);
		mockMvc.perform(get("/v1/users/{userId}/verify", 2L))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is(false)));
	}
}
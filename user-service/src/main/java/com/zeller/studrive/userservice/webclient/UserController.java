package com.zeller.studrive.userservice.webclient;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

	private final UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping(path = "/")
	public CreateUserResponse createUser(@RequestBody User user) {

		User createdUser = userService.save(user);
		return new CreateUserResponse(createdUser.getId());
	}

	@GetMapping(path = "/{userId}")
	public Optional<User> findUserById(@PathVariable Long userId) {
		return userService.getById(userId);
	}

	@PutMapping(path = "/{userId}/paymentDetails")
	public ResponseEntity<PaymentDetailsResponse> updatePaymentDetails(@PathVariable Long userId,
																	   @RequestBody PaymentDetails paymentDetails) {
		return createResponseEntity(userService.updatePaymentDetails(userId, paymentDetails));
	}

	private ResponseEntity<PaymentDetailsResponse> createResponseEntity(Optional<User> user) {
		return user.map(value -> new ResponseEntity<>(new PaymentDetailsResponse(value), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping(path = "/{userId}/verify")
	public boolean verifyPaymentDetails(@PathVariable Long userId) {
		return userService.verifyPaymentDetails(userId);
	}
}

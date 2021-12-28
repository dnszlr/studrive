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
	public ResponseEntity<CreateUserResponse> createUser(@RequestBody CreateUserRequest createUserRequest) {

		User createdUser = userService.save(new User(createUserRequest));
		return createdUser != null ? new ResponseEntity<>(new CreateUserResponse(createdUser.getId()), HttpStatus.OK) :
				new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping(path = "/{userId}")
	public ResponseEntity<User> findUserById(@PathVariable Long userId) {
		Optional<User> user = userService.getById(userId);
		return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@PutMapping(path = "/{userId}/paymentDetails")
	public ResponseEntity<PaymentDetailsResponse> updatePaymentDetails(@PathVariable Long userId,
																	   @RequestBody PaymentDetails paymentDetails) {
		return createResponseEntity(userService.updatePaymentDetails(userId, paymentDetails));
	}

	/**
	 * Creates the appropriate ResponseEntity for the passed optional. If the optional contains null the HttpStatus NOT_FOUND is returned.
	 * If the optional contains an entity, it will be returned with HttpStatus OK.
	 *
	 * @param user - The optional which contains either a user or null
	 * @return A new response entity that provides information about the outcome of the operation
	 */
	private ResponseEntity<PaymentDetailsResponse> createResponseEntity(Optional<User> user) {
		return user.map(value ->
				new ResponseEntity<>(new PaymentDetailsResponse(value), HttpStatus.OK)).orElseGet(() ->
				new ResponseEntity<>(HttpStatus.NOT_FOUND));
	}

	@GetMapping(path = "/{userId}/verify")
	public boolean verifyPaymentDetails(@PathVariable Long userId) {
		return userService.verifyPaymentDetails(userId);
	}
}

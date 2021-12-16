package com.zeller.studrive.userservice.service;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;

	/**
	 * Checks if the passed user is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param user - the user to be saved
	 * @return the newly created user or null
	 */
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * Determines a user based on the passed id
	 *
	 * @param id - The id of the user
	 * @return the user
	 */
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

	/**
	 * Update the given users payment details
	 *
	 * @param userId         - the user to be updated
	 * @param paymentDetails - the paymentDetails that should be updated for the user
	 * @return the updated user or null
	 */
	public Optional<User> updatePaymentDetails(Long userId, PaymentDetails paymentDetails) {
		Optional<User> userTemp = userRepository.findById(userId);
		if(userTemp.isPresent()) {
			User userEntity = userTemp.get();
			PaymentDetails upd = userEntity.getPaymentDetails();
			if(upd == null || !upd.attributeEquals(paymentDetails)) {
				userEntity.setPaymentDetails(paymentDetails);
				userRepository.save(userEntity);
			}
		}
		return userTemp;
	}

	/**
	 * Verification whether the user has deposited payment information
	 *
	 * @param userId - The id of the user whose payment information is to be verified
	 * @return true if the user has deposited payment information, false if not
	 */
	public boolean verifyPaymentDetails(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		return user.isPresent() && user.get().getPaymentDetails() != null;
	}
}

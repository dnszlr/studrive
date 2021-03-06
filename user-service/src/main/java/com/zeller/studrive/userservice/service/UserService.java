package com.zeller.studrive.userservice.service;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserService {

	private final UserRepository userRepository;
	final Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	/**
	 * Checks if the passed user is a new entry in the database.
	 * If yes, it will be saved, if not, the existing entry will be updated.
	 *
	 * @param user - the user to be saved
	 * @return the newly created user or null
	 */
	@Transactional(rollbackFor = IllegalArgumentException.class)
	public User save(User user) {
		return userRepository.save(user);
	}

	/**
	 * Determines a user based on the passed id
	 *
	 * @param id - The id of the user
	 * @return the user
	 */
	public Optional<User> getById(Long id) {
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
				this.save(userEntity);
				logger.info("UserService.updatePaymentDetails: Payment information for the user with the id " + userEntity.getId() + " " +
						"updated.");
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

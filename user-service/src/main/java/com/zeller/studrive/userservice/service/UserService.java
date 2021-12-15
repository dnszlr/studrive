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

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> updatePaymentDetails(Long userId, PaymentDetails paymentDetails) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User userEntity = user.get();
            userEntity.setPaymentDetails(paymentDetails);
            userRepository.save(userEntity);
        }
        return user;
    }

    public boolean verifyPaymentDetails(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.isPresent() && user.get().getPaymentDetails() != null;
    }
}

package com.zeller.studrive.userservice.webclient;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.service.UserService;
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
    public User createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @GetMapping(path = "/{userId}")
    public Optional<User> findUserById(@PathVariable Long userId) {
        return userService.getById(userId);
    }

    // TODO NUR ID UND PAYMENTDETAILS
    @PutMapping(path = "/{userId}/paymentDetails")
    public Optional<User> updatePaymentDetails(@PathVariable Long userId, @RequestBody PaymentDetails paymentDetails) {
        return userService.updatePaymentDetails(userId, paymentDetails);
    }

    @GetMapping(path = "/{userId}/verify")
    public boolean verifyPaymentDetails(@PathVariable Long userId) {
        return userService.verifyPaymentDetails(userId);
    }
}

package com.zeller.studrive.userservice.webclient;

import com.zeller.studrive.userservice.model.PaymentDetails;
import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Checks if the passed user is a new entry in the database.
     * If yes, it will be saved, if not, the existing entry will be updated.
     *
     * @param user - the user to be saved
     * @return the newly created user or null
     */
    @PostMapping(path = "/create")
    public User create(@RequestBody User user) {
        return userService.save(user);
    }

    /**
     * Determines a user based on the passed id
     *
     * @param userId - The id of the user
     * @return the user or null
     */
    @GetMapping(path = "/{userId}")
    public Optional<User> get(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    /**
     * @param userId         - the user to be updated
     * @param paymentDetails - the paymentDetails that should be updated for the user
     * @return the updated user or null
     */
    @PutMapping(path = "/{userId}/paymentDetails")
    public Optional<User> update(@PathVariable Long userId, @RequestBody PaymentDetails paymentDetails) {
        return userService.updatePaymentDetails(userId, paymentDetails);
    }

    /**
     * Verification whether the user has deposited payment information
     *
     * @param userId - The id of the user whose payment information is to be verified
     * @return true if the user has deposited payment information, false if not
     */
    @GetMapping(path = "/{userId}/verify")
    public boolean verifyPaymentDetails(@PathVariable Long userId) {
        return userService.verifyPaymentDetails(userId);
    }

}

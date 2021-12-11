package com.zeller.studrive.userservice.webclient;

import com.zeller.studrive.userservice.model.User;
import com.zeller.studrive.userservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/v1/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "/changeTest")
    public String hello() {
        return "hello";
    }

    @GetMapping(path = "/save")
    public void saveTest() {
        User user = new User();
        user.setFirstName("Dennis");
        user.setLastName("Zeller");
        user.setEmail("test@pgadmin.de");
        userService.save(user);
    }


}

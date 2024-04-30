package com.OTPGeneration.Controller;

import com.OTPGeneration.Entity.User;
import com.OTPGeneration.Service.EmailService;
import com.OTPGeneration.Service.UserService;
import com.OTPGeneration.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private UserService userService; // Service layer class

    @Autowired
    private EmailService emailService; // Service layer class


    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);

        //Send OTP For email verification
        emailService.sendOtpEmail(user.getEmail());

        return null;
    }
}

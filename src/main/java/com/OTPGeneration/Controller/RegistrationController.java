package com.OTPGeneration.Controller;

import com.OTPGeneration.Entity.User;
import com.OTPGeneration.Service.EmailService;
import com.OTPGeneration.Service.EmailVerificationService;
import com.OTPGeneration.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class RegistrationController {

    @Autowired
    private UserService userService; // Service layer class

    @Autowired
    private EmailService emailService; // Service layer class

    @Autowired
    private EmailVerificationService emailVerificationService;

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody User user) {// aFor store the JSON key value pair
        User registeredUser = userService.registerUser(user);

        // Send OTP For email verification
        emailService.sendOtpEmail(user.getEmail());

        Map<String, String> response = new HashMap<>(); // Response for the  verify the OTP
        response.put("status", "success");// Store the response as key value pair
        response.put("message", "User Registered Successfully. Check your email for OTP verification");
        return response;
    }

    @PostMapping("/verify")
    public Map<String, String> verifyOtp(@RequestParam String email, @RequestParam String otp) {
        return emailVerificationService.verifyOtpForLogin(email, otp);
    }
}

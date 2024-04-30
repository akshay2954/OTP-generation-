package com.OTPGeneration.Controller;

import com.OTPGeneration.Entity.User;
import com.OTPGeneration.Service.EmailService;
import com.OTPGeneration.Service.EmailVerificationService;
import com.OTPGeneration.Service.UserService;
import com.OTPGeneration.Service.UserService;
import org.hibernate.dialect.function.array.AbstractArrayTrimFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.PreparedStatement;
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
    private EmailVerificationService emailVerificationService ;

    @PostMapping("/register")
    public Map<String, String> registerUser(@RequestBody User user) {// For store the JSON key value pair
        User registeredUser = userService.registerUser(user);

        //Send OTP For email verification
        emailService.sendOtpEmail(user.getEmail());

        Map<String,String> response = new HashMap<>(); // response for the verifiy the OTP
        response.put("status","success");// Store the response as key value pair
        response.put("message", "User Register Successfully. Check your email for verification");
        return response;
    }

    @PostMapping("/verifyOtp")
    public Map<String, String> verifyOtp(@RequestParam String email, @RequestParam String otp){
        return EmailVerificationService.verifyOtp(email,otp);
    }

 }

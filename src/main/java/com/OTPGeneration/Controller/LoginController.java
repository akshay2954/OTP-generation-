package com.OTPGeneration.Controller;

import com.OTPGeneration.Service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class LoginController {

    private final EmailVerificationService emailVerificationService;

    @Autowired
    public LoginController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/send-otp-login")
    public Map<String, String> sendOtpForLogin(@RequestParam String email) {
        return emailVerificationService.sendOtpForLogin(email);
    }

    @PostMapping("/Verify-otp")
    public Map<String, String> verifyOtpForLogin(@RequestParam String email, @RequestParam String otp) {
        return emailVerificationService.verifyOtpForLogin(email, otp);
    }
}


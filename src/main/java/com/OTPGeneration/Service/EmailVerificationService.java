package com.OTPGeneration.Service;

import com.OTPGeneration.Entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailVerificationService {

    private final UserService userService;
    private final EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(EmailVerificationService.class);

    private final Map<String, String> emailOtpMapping = new HashMap<>(); // HashMap used to store temporary OTPs

    @Autowired
    public EmailVerificationService(UserService userService, EmailService emailService) {
        this.userService = userService;
        this.emailService = emailService;
    }

    public Map<String, String> verifyOtp(String email, String otp) {
        String storedOtp = emailOtpMapping.get(email);

        Map<String, String> response = new HashMap<>();
        if (storedOtp != null && storedOtp.equals(otp)) {

            User user = userService.getUserByEmail(email);
            if (user != null) {
                // Remove the OTP from HashMap after successful verification
                emailOtpMapping.remove(email);
                userService.verifyEmail(user);
                response.put("status", "success");
                response.put("message", "Email verified successfully");
            } else {
                logger.error("User not found for email: {}", email);
                response.put("status", "error");
                response.put("message", "User not found");
            }
        } else {
            logger.error("Invalid OTP for email: {}", email);
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }
        return response;
    }

    public Map<String, String> sendOtpForLogin(String email) {
        if (userService.isEmailVerified(email)) {
            String otp = emailService.generateOtp();
            // Save the OTP for later verification
            emailOtpMapping.put(email, otp);

            // Send OTP to the User Email
            emailService.sendOtpEmail(email);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "OTP sent successfully");
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Email not verified");
            return response;
        }
    }

    public Map<String, String> verifyOtpForLogin(String email, String otp) {
        // Call verifyOtp method as it's essentially the same process for login verification
        return verifyOtp(email, otp);
    }
}

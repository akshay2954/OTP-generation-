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

    @Autowired
    private static UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(EmailVerificationService.class);

    static final Map<String, String> emailOtpMapping = new HashMap<>(); // HashMap used to store temporary OTPs

    public static Map<String,String> verifyOtp(String email, String otp){
        String storeOtp = emailOtpMapping.get(email);

        Map<String ,String > response = new HashMap<>();
        if(storeOtp != null && storeOtp.equals(otp)) {
            User user = userService.getUserByEmail(email);
            if(user != null){
                emailOtpMapping.remove(email); // remove the Otp form HashMap
                userService.verifyEmail(user);
                response.put("status", "success");
                response.put("message", "Email verified successfully");
            } else {
                logger.error("Invalid OTP Register for Email: {}", email);
                response.put("status", "error");
                response.put("message", "User not found");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Invalid OTP");
        }
        return response;
    }
}

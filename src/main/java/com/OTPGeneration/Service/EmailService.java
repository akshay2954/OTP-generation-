package com.OTPGeneration.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final Map<String, String> emailOtpMapping;

    @Autowired
    public EmailService(JavaMailSender javaMailSender, UserService userService) {
        this.javaMailSender = javaMailSender;
        this.userService = userService;
        this.emailOtpMapping = new HashMap<>();
    }

    public String generateOtp() {
        return String.format("%06d", new java.util.Random().nextInt(1000000)); // Otp generate random Number
    }// This method generates a 6-digit OTP Number to the email or mobile Number

    public void sendOtpEmail(String email) {
        String otp = generateOtp();

        // Save the OTP for Later verifications
        emailOtpMapping.put(email, otp);

        sendEmail(email, "OTP for Email Verification", "Your OTP is: " + otp);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your.email@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javaMailSender.send(message);
    }
}

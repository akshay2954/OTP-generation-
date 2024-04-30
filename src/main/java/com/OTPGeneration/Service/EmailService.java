package com.OTPGeneration.Service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static com.OTPGeneration.Service.EmailVerificationService.emailOtpMapping; // Static Import


@Service
public class EmailService {

    @Autowired
    private JavaMailSender javamailSender;
    private final UserService userService;


    @Autowired
    public EmailService(JavaMailSender javamailSender, UserService userService) { // Constructor Base Injections
        this.javamailSender = javamailSender;
        this.userService = userService;

        // Enable debug logging

    }

    public String generateOtp() {
        return String.format("%06d", new java.util.Random().nextInt(1000000)); // Otp generate random Number
    }//                                 This  method is generate the 6 digit OTP Number to the email or mobile Number

    public void sendOtpEmail(String email) {

        String otp = generateOtp();

        // Save the Otp for Later verifications
        emailOtpMapping.put(email, otp);

        sendEmail(email, "Otp for Email Varification", "your OTP is:" + otp);
    }

    private void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("your.email@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        javamailSender.send(message);
    }
}

package com.OTPGeneration.Service;

import com.OTPGeneration.Entity.User;
import com.OTPGeneration.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Struct;

@Service
public class UserService {

    @Autowired
     private UserRepository userRepository;

    public User registerUser (User user) {
        return userRepository.save(user);// Save the user to database
    }

    public User getUserByEmail(String email) {
       User user= userRepository.findByEmail(email); // find the email in database
       return user;
    }

    public void verifyEmail(User user) {
        user.setEmailVerified(true);
        userRepository.save(user);
    }
}

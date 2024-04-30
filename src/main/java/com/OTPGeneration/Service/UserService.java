package com.OTPGeneration.Service;

import com.OTPGeneration.Entity.User;
import com.OTPGeneration.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
     private UserRepository userRepository;

    public User registerUser (User user) {
        return userRepository.save(user);// Save the user to database
    }

}

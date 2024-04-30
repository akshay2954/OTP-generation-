package com.OTPGeneration.Repository;

import com.OTPGeneration.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,  Long> {

    User findByEmail (String email); // Find the Email ID
}

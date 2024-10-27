package com.example.eventmanagement.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.entity.User;
import com.example.eventmanagement.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // Other existing methods...

    // Method to register a user
    public void register(User user) {
        // Your existing registration logic, e.g., email verification setup
        userRepository.save(user);
    }

    // Method to verify a user's email
    public void verifyUser(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent()) {
            user.get().setVerified(true);
            userRepository.save(user.get());
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // New method to get user by ID
    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> 
            new IllegalArgumentException("User not found"));
    }
}

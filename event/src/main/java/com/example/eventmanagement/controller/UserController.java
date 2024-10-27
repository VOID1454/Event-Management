package com.example.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.entity.User;
import com.example.eventmanagement.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private UserService userService;

    // User Registration
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.register(user);
            return ResponseEntity.ok("Registration successful. Please verify your email.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration failed: " + e.getMessage());
        }
    }

    // Email Verification
    @GetMapping("/verify")
    public ResponseEntity<String> verifyUser(@RequestParam String email) {
        try {
            userService.verifyUser(email);
            return ResponseEntity.ok("Email verified successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed: " + e.getMessage());
        }
    }

    // Optional: Get user details (e.g., for admin purposes)
    @GetMapping("/details")
    public ResponseEntity<User> getUserDetails(@RequestParam Long userId) {
        try {
            User user = userService.getUserById(userId); // Assume you have this method in UserService
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}

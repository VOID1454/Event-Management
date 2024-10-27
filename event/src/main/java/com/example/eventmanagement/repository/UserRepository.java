package com.example.eventmanagement.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventmanagement.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Find a user by email
    Optional<User> findByEmail(String email);

    // Find a user by contact number
    Optional<User> findByContactNo(String contactNo);

    // Check if a user exists by email
    boolean existsByEmail(String email);

    // Find users by verification status
    List<User> findByIsVerified(boolean isVerified);

    // Delete a user by email
    void deleteByEmail(String email);
}

package com.example.eventmanagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventmanagement.entity.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    // Find an admin by email (useful for login or verification purposes)
    Optional<Admin> findByEmail(String email);

    // Find an admin by contact number (if you ever need to search by contact)
    Optional<Admin> findByContactNo(String contactNo);

    // Delete an admin by email
    void deleteByEmail(String email);

    // Check if an admin exists with a given email
    boolean existsByEmail(String email);
}

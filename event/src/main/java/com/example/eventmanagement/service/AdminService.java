package com.example.eventmanagement.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.dto.AdminLoginDTO;
import com.example.eventmanagement.entity.Admin;
import com.example.eventmanagement.entity.Log;
import com.example.eventmanagement.repository.AdminRepository;
import com.example.eventmanagement.repository.LogRepository;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder; // For password hashing

    public boolean login(AdminLoginDTO adminLoginDTO) {
        Optional<Admin> admin = adminRepository.findByEmail(adminLoginDTO.getEmail());
        if (admin.isPresent() && passwordEncoder.matches(adminLoginDTO.getPassword(), admin.get().getPassword())) {
            return true;
        }
        return false;
    }

    public void logAdminLogin(String email, String ipAddress) {
        Admin admin = adminRepository.findByEmail(email).orElseThrow(() -> 
            new IllegalArgumentException("Admin not found"));
        Log log = new Log();
        log.setAdmin(admin);
        log.setIpAddress(ipAddress);
        logRepository.save(log);
    }

    // Add a new admin
    public Admin addAdmin(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword())); // Hash the password
        return adminRepository.save(admin);
    }

    // Update admin details
    public Admin updateAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Delete admin by ID
    public void deleteAdmin(Long adminId) {
        adminRepository.deleteById(adminId);
    }

    // Get a list of all admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }
}

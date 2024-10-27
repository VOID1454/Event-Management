package com.example.eventmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventmanagement.dto.AdminLoginDTO;
import com.example.eventmanagement.entity.Admin;
import com.example.eventmanagement.entity.Event;
import com.example.eventmanagement.service.AdminService;
import com.example.eventmanagement.service.EventService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private EventService eventService;
    
    @Autowired
    private AdminService adminService;

    private static final String RECAPTCHA_SECRET_KEY = "YOUR_SECRET_KEY"; // Update with your secret key
    private static final String RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @PostMapping("/events")
    public ResponseEntity<String> addEvent(@RequestBody Event event) {
        eventService.addEvent(event);
        return ResponseEntity.ok("Event added successfully");
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted successfully");
    }

    @PutMapping("/events")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(event);
        return ResponseEntity.ok(updatedEvent);
    }

    @PostMapping("/login")
    public ResponseEntity<String> adminLogin(@RequestBody AdminLoginDTO adminLoginDTO, HttpServletRequest request) {
        String captchaResponse = adminLoginDTO.getCaptchaResponse(); // Ensure your DTO has a field for captcha response
        if (!verifyCaptcha(captchaResponse)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Captcha verification failed");
        }

        boolean isLoggedIn = adminService.login(adminLoginDTO);

        if (isLoggedIn) {
            String ipAddress = request.getRemoteAddr();
            adminService.logAdminLogin(adminLoginDTO.getEmail(), ipAddress);
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
    }

    // Endpoint to add a new admin
    @PostMapping("/admins")
    public ResponseEntity<String> addAdmin(@RequestBody Admin admin) {
        adminService.addAdmin(admin);
        return ResponseEntity.ok("Admin added successfully");
    }

    // Endpoint to update an existing admin
    @PutMapping("/admins")
    public ResponseEntity<Admin> updateAdmin(@RequestBody Admin admin) {
        Admin updatedAdmin = adminService.updateAdmin(admin);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Endpoint to delete an admin by ID
    @DeleteMapping("/admins/{adminId}")
    public ResponseEntity<String> deleteAdmin(@PathVariable Long adminId) {
        adminService.deleteAdmin(adminId);
        return ResponseEntity.ok("Admin deleted successfully");
    }

    // Endpoint to get a list of all admins
    @GetMapping("/admins")
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    private boolean verifyCaptcha(String captchaResponse) {
        RestTemplate restTemplate = new RestTemplate();
        String url = RECAPTCHA_VERIFY_URL + "?secret=" + RECAPTCHA_SECRET_KEY + "&response=" + captchaResponse;

        try {
            String response = restTemplate.getForObject(url, String.class);
            return response.contains("\"success\": true"); 
        } catch (HttpClientErrorException e) {
            // Log error and return false if there is an issue
            return false;
        }
    }
}

// Global exception handler for the entire application
@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // You can add more handlers for different exceptions here, if needed
}

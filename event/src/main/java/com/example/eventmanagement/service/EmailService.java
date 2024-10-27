package com.example.eventmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.entity.User;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendVerificationEmail(User user) {
        String verificationUrl = "http://localhost:8080/user/verify?email=" + user.getEmail();
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Email Verification");
        message.setText("Click on the following link to verify your email: " + verificationUrl);
        mailSender.send(message);
    }
}


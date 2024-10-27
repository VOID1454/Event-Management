package com.example.eventmanagement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EventApplication {

    private static final Logger logger = LoggerFactory.getLogger(EventApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(EventApplication.class, args);
        logger.info("Event Management Application Started Successfully");
    }
}
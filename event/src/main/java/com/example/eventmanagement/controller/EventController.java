package com.example.eventmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.eventmanagement.entity.Event;
import com.example.eventmanagement.service.EventService;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    
    @Autowired
    private EventService eventService;

    // Create a new event
    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody Event event) {
        eventService.addEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body("Event created successfully");
    }

    // Get all events
    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEventsWithinMonth(); // Adjust method as needed
        return ResponseEntity.ok(events);
    }

    // Get event by ID
    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId) {
        // You may need to implement a method in EventService to fetch by ID
        Event event = eventService.getEventById(eventId); // Implement this method in EventService
        return ResponseEntity.ok(event);
    }

    // Update an existing event
    @PutMapping
    public ResponseEntity<Event> updateEvent(@RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(event);
        return ResponseEntity.ok(updatedEvent);
    }

    // Delete an event by ID
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted successfully");
    }
}

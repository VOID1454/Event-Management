package com.example.eventmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventmanagement.entity.Event;
import com.example.eventmanagement.repository.EventRepository;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public List<Event> getAllEventsWithinMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneMonthLater = now.plusMonths(1);
        return eventRepository.findByEventStartDateBetween(now, oneMonthLater);
    }

    public void addEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(Long eventId) {
        eventRepository.deleteById(eventId);
    }

    public Event updateEvent(Event event) {
        return eventRepository.save(event);
    }

    // New method to fetch event by ID
    public Event getEventById(Long eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
            new IllegalArgumentException("Event not found with ID: " + eventId));
    }
}

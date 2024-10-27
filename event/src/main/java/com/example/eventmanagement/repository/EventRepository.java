package com.example.eventmanagement.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventmanagement.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // Find events that start between two dates
    List<Event> findByEventStartDateBetween(LocalDateTime startDate, LocalDateTime endDate);

    // Find an event by its name (case insensitive)
    List<Event> findByEventNameIgnoreCase(String eventName);

    // Find events that are scheduled to start after the current date/time (upcoming events)
    List<Event> findByEventStartDateAfter(LocalDateTime currentDate);

    // Find events that have already ended (past events)
    List<Event> findByEventEndDateBefore(LocalDateTime currentDate);

    // Delete events by name
    void deleteByEventName(String eventName);
}

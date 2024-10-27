package com.example.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.eventmanagement.entity.Log;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}

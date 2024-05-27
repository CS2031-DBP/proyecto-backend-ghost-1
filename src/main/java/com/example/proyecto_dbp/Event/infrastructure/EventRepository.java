package com.example.proyecto_dbp.Event.infrastructure;

import com.example.proyecto_dbp.Event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByCourseId(Long courseId);
}

package com.example.proyecto_dbp.Event.infrastructure;

import com.example.proyecto_dbp.Event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {
}

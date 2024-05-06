package com.example.proyecto_dbp.CalendarEvent.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyecto_dbp.CalendarEvent.domain.CalendarEvent;

public interface CalendarEventRepository extends JpaRepository<CalendarEvent, Long> {
}

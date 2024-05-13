package com.example.proyecto_dbp.Event.infrastructure;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.proyecto_dbp.Event.domain.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}

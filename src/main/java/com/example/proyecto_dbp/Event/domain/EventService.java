package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.Event.infrastructure.EventRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<Event> getAllEvents() {return eventRepository.findAll();}

    public Event getEventById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    public List<Event> getEventsByCourseId(Long courseId) {
        List<Event> events = eventRepository.findByCourseId(courseId);
        if (events.isEmpty()) throw new ResourceNotFoundException("No events found for course with id " + courseId);
        return events;
    }


    public Event createEvent(Event event) {return eventRepository.save(event);}

    public Event updateEvent(Long id, Event updatedEvent) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        event.setTitulo(updatedEvent.getTitulo());
        event.setDescripcion(updatedEvent.getDescripcion());
        event.setFechaInicio(updatedEvent.getFechaInicio());
        event.setFechaFin(updatedEvent.getFechaFin());
        event.setEstado(updatedEvent.getEstado());
        event.setLocation(updatedEvent.getLocation());
        event.setAllDay(updatedEvent.getAllDay());
        event.setOrganizer(updatedEvent.getOrganizer());
        event.setAttendees(updatedEvent.getAttendees());
        event.setReminder(updatedEvent.getReminder());

        return eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) throw new ResourceNotFoundException("Event not found with id " + id);
        eventRepository.deleteById(id);
    }
}

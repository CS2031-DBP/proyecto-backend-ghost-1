package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.Event.dto.EventDTO;
import com.example.proyecto_dbp.Event.infrastructure.EventRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO getEventById(Long id) {
        return eventRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
    }

    public List<EventDTO> getEventsByCourseId(Long courseId) {
        return eventRepository.findByCourseId(courseId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = convertToEntity(eventDTO);
        event = eventRepository.save(event);
        return convertToDTO(event);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));

        event.setTitulo(eventDTO.getTitulo());
        event.setDescripcion(eventDTO.getDescripcion());
        event.setFechaInicio(eventDTO.getFechaInicio());
        event.setFechaFin(eventDTO.getFechaFin());
        event.setEstado(eventDTO.getEstado());
        event.setLocation(eventDTO.getLocation());
        event.setAllDay(eventDTO.getAllDay());
        event.setOrganizer(eventDTO.getOrganizer());
        event.setAttendees(Collections.singletonList(eventDTO.getAttendees()));
        event.setReminder(eventDTO.getReminder());

        event = eventRepository.save(event);
        return convertToDTO(event);
    }

    public void deleteEvent(Long id) {eventRepository.deleteById(id);}

    private EventDTO convertToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitulo(event.getTitulo());
        eventDTO.setDescripcion(event.getDescripcion());
        eventDTO.setFechaInicio(event.getFechaInicio());
        eventDTO.setFechaFin(event.getFechaFin());
        eventDTO.setEstado(event.getEstado());
        eventDTO.setLocation(event.getLocation());
        eventDTO.setAllDay(event.getAllDay());
        eventDTO.setOrganizer(event.getOrganizer());
        eventDTO.setReminder(event.getReminder());
        return eventDTO;
    }

    private Event convertToEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setTitulo(eventDTO.getTitulo());
        event.setDescripcion(eventDTO.getDescripcion());
        event.setFechaInicio(eventDTO.getFechaInicio());
        event.setFechaFin(eventDTO.getFechaFin());
        event.setEstado(eventDTO.getEstado());
        event.setLocation(eventDTO.getLocation());
        event.setAllDay(eventDTO.getAllDay());
        event.setOrganizer(eventDTO.getOrganizer());
        event.setAttendees(Collections.singletonList(eventDTO.getAttendees()));
        event.setReminder(eventDTO.getReminder());
        return event;
    }
}

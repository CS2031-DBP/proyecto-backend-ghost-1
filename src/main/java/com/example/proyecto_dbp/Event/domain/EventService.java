package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.Event.dto.EventDTO;
import com.example.proyecto_dbp.Event.infrastructure.EventRepository;
import com.example.proyecto_dbp.Event.events.EventCreatedEvent;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = new Event();
        event = eventRepository.save(event);

        eventPublisher.publishEvent(new EventCreatedEvent(this, eventDTO.getEmail()));

        return convertToDTO(event);
    }

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EventDTO getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        return convertToDTO(event);
    }

    public List<EventDTO> getEventsByCourseId(Long courseId) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getCourse().getId().equals(courseId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Event not found with id " + id));
        event.setTitle(eventDTO.getTitulo());
        event.setDescription(eventDTO.getDescripcion());
        event.setStartTime(eventDTO.getFechaInicio());
        event.setEndTime(eventDTO.getFechaFin());
        event.setStatus(eventDTO.getEstado());
        event.setLocation(eventDTO.getLocation());
        event.setAllDay(eventDTO.isAllDay());
        event.setOrganizer(eventDTO.getOrganizer());
        event.setAttendees(eventDTO.getAttendees());
        event.setReminder(eventDTO.getReminder());
        event = eventRepository.save(event);
        return convertToDTO(event);
    }

    public void deleteEvent(Long id) {
        if (!eventRepository.existsById(id)) {
            throw new ResourceNotFoundException("Event not found with id " + id);
        }
        eventRepository.deleteById(id);
    }

    private EventDTO convertToDTO(Event event) {
        return EventDTO.eventBuilder()
                .id(event.getId())
                .titulo(event.getTitle())
                .descripcion(Long.valueOf(event.getDescription()))
                .fechaInicio(event.getStartTime())
                .fechaFin(event.getEndTime())
                .estado(event.getStatus())
                .courseId(String.valueOf(event.getCourse().getId()))
                .location(event.getLocation())
                .allDay(event.isAllDay())
                .organizer(event.getOrganizer())
                .attendees(event.getAttendees())
                .reminder(event.getReminder())
                .build();
    }

    private Event convertToEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitulo());
        event.setDescription(eventDTO.getDescripcion());
        event.setStartTime(eventDTO.getFechaInicio());
        event.setEndTime(eventDTO.getFechaFin());
        event.setStatus(eventDTO.getEstado());
        event.setLocation(eventDTO.getLocation());
        event.setAllDay(eventDTO.isAllDay());
        event.setOrganizer(eventDTO.getOrganizer());
        event.setAttendees(eventDTO.getAttendees());
        event.setReminder(eventDTO.getReminder());
        courseRepository.findById(eventDTO.getCourseId()).ifPresent(event::setCourse);
        return event;
    }
}

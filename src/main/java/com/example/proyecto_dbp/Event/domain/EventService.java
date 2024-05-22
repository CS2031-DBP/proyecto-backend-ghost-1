package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.Event.dto.EventDTO;
import com.example.proyecto_dbp.Event.infrastructure.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CourseRepository courseRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<EventDTO> getEventById(Long id) {
        return eventRepository.findById(id).map(this::convertToDTO);
    }

    public List<EventDTO> getEventsByCourseId(Long courseId) {
        return eventRepository.findAll().stream()
                .filter(event -> event.getCourse().getCourse_id().equals(courseId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = convertToEntity(eventDTO);
        event = eventRepository.save(event);
        return convertToDTO(event);
    }

    public EventDTO updateEvent(Long id, EventDTO eventDTO) {
        Optional<Event> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            event.setTitle(eventDTO.getTitulo());
            event.setDescription(eventDTO.getDescripcion());
            event.setStartTime(eventDTO.getFechaInicio());
            event.setEndTime(eventDTO.getFechaFin());
            event.setStatus(eventDTO.getEstado());
            event = eventRepository.save(event);
            return convertToDTO(event);
        }
        return null;
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    private EventDTO convertToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitulo(event.getTitle());
        eventDTO.setDescripcion(event.getDescription());
        eventDTO.setFechaInicio(event.getStartTime());
        eventDTO.setFechaFin(event.getEndTime());
        eventDTO.setEstado(event.getStatus());
        eventDTO.setCourseId(event.getCourse().getCourse_id());
        return eventDTO;
    }

    private Event convertToEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitle(eventDTO.getTitulo());
        event.setDescription(eventDTO.getDescripcion());
        event.setStartTime(eventDTO.getFechaInicio());
        event.setEndTime(eventDTO.getFechaFin());
        event.setStatus(eventDTO.getEstado());
        courseRepository.findById(eventDTO.getCourseId()).ifPresent(event::setCourse);
        return event;
    }
}

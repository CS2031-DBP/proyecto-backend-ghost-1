package com.example.proyecto_dbp.Event.domain;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.Event.dto.EventDTO;
import com.example.proyecto_dbp.Event.infrastructure.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public List<EventDTO> getAllEvents() {
        return eventRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public EventDTO createEvent(EventDTO eventDTO) {
        Event event = convertToEntity(eventDTO);
        event = eventRepository.save(event);
        return convertToDTO(event);
    }

    // Other service methods

    private EventDTO convertToDTO(Event event) {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setId(event.getId());
        eventDTO.setTitulo(event.getTitulo());
        eventDTO.setDescripcion(event.getDescripcion());
        eventDTO.setFechaInicio(event.getFechaInicio());
        eventDTO.setFechaFin(event.getFechaFin());
        eventDTO.setEstado(event.getEstado());
        eventDTO.setCourseId(event.getCourse().getId());
        return eventDTO;
    }

    private Event convertToEntity(EventDTO eventDTO) {
        Event event = new Event();
        event.setTitulo(eventDTO.getTitulo());
        event.setDescripcion(eventDTO.getDescripcion());
        event.setFechaInicio(eventDTO.getFechaInicio());
        event.setFechaFin(eventDTO.getFechaFin());
        event.setEstado(eventDTO.getEstado());
        activityRepository.findById(eventDTO.getCourseId()).ifPresent(event::setCourse);
        return event;
    }
}

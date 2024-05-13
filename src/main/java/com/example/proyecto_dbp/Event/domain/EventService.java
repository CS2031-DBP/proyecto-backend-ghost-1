package com.example.proyecto_dbp.Event.domain;
import com.example.proyecto_dbp.Event.infrastructure.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public Event getEvent(long id){
        return eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }
    public void saveEvent(Event event) {
        eventRepository.save(event);
    }

    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    public void updateEvent(Long id, Event event) {
        Event EventToUpdate = eventRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        EventToUpdate.setTitle(event.getTitle());
        EventToUpdate.setEndTime(event.getEndTime());
        EventToUpdate.setStartTime(event.getStartTime());
        eventRepository.save(EventToUpdate);
    }
    public void updateEndTime(Long id, LocalDate endtime){
        Event EventToUpdate = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        EventToUpdate.setEndTime(endtime);
        eventRepository.save(EventToUpdate);
    }
    public void updateTitle(Long id, String title){
        Event EventToUpdate = eventRepository.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        EventToUpdate.setTitle(title);
        eventRepository.save(EventToUpdate);
    }
}

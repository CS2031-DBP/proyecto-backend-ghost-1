package com.example.proyecto_dbp.Event.application;

import com.example.proyecto_dbp.Event.domain.Event;
import com.example.proyecto_dbp.Event.domain.EventService;
import com.example.proyecto_dbp.Event.dto.EventDTO;
import com.example.proyecto_dbp.Event.events.EmailService;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-test-email")
    public ResponseEntity<String> sendTestEmail(@RequestParam String email) throws MessagingException {
        emailService.sendHtmlMessage(email, "Correo de Prueba", "<h1>Este es un correo de prueba</h1>");
        return ResponseEntity.ok("Correo enviado exitosamente");
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEventById(@PathVariable Long id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<Event>> getEventsByCourseId(@PathVariable Long courseId) {
        List<Event> events = eventService.getEventsByCourseId(courseId);
        return ResponseEntity.ok(events);
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Event> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        Event updatedEvent = eventService.updateEvent(id, event);
        return ResponseEntity.ok(updatedEvent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
}

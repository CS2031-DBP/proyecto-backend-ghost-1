package com.example.proyecto_dbp.Event.application;
import com.example.proyecto_dbp.Event.domain.EventService;
import com.example.proyecto_dbp.Event.domain.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/event")
public class EventController {
    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<Event> getEvent(@PathVariable Long id){
        return ResponseEntity.ok(eventService.getEvent(id));
    }
    @PostMapping()
    public ResponseEntity<Void> saveEvent(@RequestBody Event event {
        eventService.saveEvent(event);
        return ResponseEntity.ok().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateEvent(@PathVariable Long id, @RequestBody Event event) {
        eventService.updateEvent(id, event);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{id}/endtime")
    public ResponseEntity<Void> updateEventTime(
            @PathVariable Long id,
            @RequestParam("endtime") LocalDate endtime
    ) {
        eventService.updateEndTime(id,endtime);
        return ResponseEntity.ok().build();
    }
    @PatchMapping("/{id}/title")
    public ResponseEntity<Void> updatetitle(@PathVariable Long id, @RequestParam("title") String title) {
        eventService.updateTitle(id,title);
        return ResponseEntity.ok().build();
    }

}

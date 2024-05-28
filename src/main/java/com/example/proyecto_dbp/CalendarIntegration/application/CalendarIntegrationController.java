package com.example.proyecto_dbp.CalendarIntegration.application;

import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegration;
import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendarIntegrations")
public class CalendarIntegrationController {

    @Autowired
    private CalendarIntegrationService calendarIntegrationService;

    @GetMapping
    public List<CalendarIntegration> getAllCalendarIntegrations() {
        return calendarIntegrationService.getAllCalendarIntegrations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CalendarIntegration> getCalendarIntegrationById(@PathVariable Long id) {
        CalendarIntegration calendarIntegration = calendarIntegrationService.getCalendarIntegrationByActivityId(id);
        return ResponseEntity.ok(calendarIntegration);
    }

    @PostMapping
    public ResponseEntity<CalendarIntegration> createCalendarIntegration(@RequestBody CalendarIntegration calendarIntegration) {
        CalendarIntegration createdCalendarIntegration = calendarIntegrationService.createCalendarIntegration(calendarIntegration);
        return new ResponseEntity<>(createdCalendarIntegration, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CalendarIntegration> updateCalendarIntegration(@PathVariable Long id, @RequestBody CalendarIntegration calendarIntegration) {
        CalendarIntegration updatedCalendarIntegration = calendarIntegrationService.updateCalendarIntegration(id, calendarIntegration);
        return ResponseEntity.ok(updatedCalendarIntegration);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalendarIntegration(@PathVariable Long id) {
        calendarIntegrationService.deleteCalendarIntegration(id);
        return ResponseEntity.noContent().build();
    }
}

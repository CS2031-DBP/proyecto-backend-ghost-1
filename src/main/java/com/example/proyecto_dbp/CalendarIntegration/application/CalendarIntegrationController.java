package com.example.proyecto_dbp.CalendarIntegration.application;

import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegration;
import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegrationService;
import com.example.proyecto_dbp.CalendarIntegration.dto.CalendarIntegrationDTO;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/calendar-integrations")
public class CalendarIntegrationController {

    @Autowired
    private CalendarIntegrationService calendarIntegrationService;

    @GetMapping
    public ResponseEntity<List<CalendarIntegration>> getAllCalendarIntegrations() {
        List<CalendarIntegration> integrations = calendarIntegrationService.getAllCalendarIntegrations();
        return ResponseEntity.ok(integrations);
    }

    @GetMapping("/{activityId}")
    public ResponseEntity<CalendarIntegration> getCalendarIntegrationByActivityId(@PathVariable Long activityId) {
        CalendarIntegration integration = calendarIntegrationService.getCalendarIntegrationByActivityId(activityId);
        if (integration != null) return ResponseEntity.ok(integration);
        else return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CalendarIntegration> createCalendarIntegration(@RequestBody @Valid CalendarIntegration calendarIntegration) {
        CalendarIntegration createdIntegration = calendarIntegrationService.createCalendarIntegration(calendarIntegration);
        return new ResponseEntity<>(createdIntegration, HttpStatus.CREATED);
    }

    @PutMapping("/{activityId}")
    public ResponseEntity<CalendarIntegration> updateCalendarIntegration(@PathVariable Long activityId, @RequestBody @Valid CalendarIntegration calendarIntegration) {
        CalendarIntegration updatedIntegration = calendarIntegrationService.updateCalendarIntegration(activityId, calendarIntegration);
        if (updatedIntegration != null) return ResponseEntity.ok(updatedIntegration);
        else return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteCalendarIntegration(@PathVariable Long activityId) {
        try {
            calendarIntegrationService.deleteCalendarIntegration(activityId);
            return ResponseEntity.noContent().build();
        }
        catch (ResourceNotFoundException e) {return ResponseEntity.notFound().build();}
    }
}

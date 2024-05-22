package com.example.proyecto_dbp.CalendarIntegration.application;

import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegrationService;
import com.example.proyecto_dbp.CalendarIntegration.dto.CalendarIntegrationDTO;
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

    // Get all calendar integrations
    @GetMapping
    public List<CalendarIntegrationDTO> getAllCalendarIntegrations() {
        return calendarIntegrationService.getAllCalendarIntegrations();
    }

    // Get calendar integration by activity ID
    @GetMapping("/{activityId}")
    public ResponseEntity<CalendarIntegrationDTO> getCalendarIntegrationByActivityId(@PathVariable Long activityId) {
        return ResponseEntity.of(calendarIntegrationService.getCalendarIntegrationByActivityId(activityId));
    }

    // Create a new calendar integration
    @PostMapping
    public ResponseEntity<CalendarIntegrationDTO> createCalendarIntegration(@RequestBody CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegrationDTO createdCalendarIntegration = calendarIntegrationService.createCalendarIntegration(calendarIntegrationDTO);
        return new ResponseEntity<>(createdCalendarIntegration, HttpStatus.CREATED);
    }

    // Update an existing calendar integration
    @PutMapping("/{activityId}")
    public ResponseEntity<CalendarIntegrationDTO> updateCalendarIntegration(@PathVariable Long activityId, @RequestBody CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegrationDTO updatedCalendarIntegration = calendarIntegrationService.updateCalendarIntegration(activityId, calendarIntegrationDTO);
        return ResponseEntity.ok(updatedCalendarIntegration);
    }

    // Delete a calendar integration
    @DeleteMapping("/{activityId}")
    public ResponseEntity<Void> deleteCalendarIntegration(@PathVariable Long activityId) {
        calendarIntegrationService.deleteCalendarIntegration(activityId);
        return ResponseEntity.noContent().build();
    }
}

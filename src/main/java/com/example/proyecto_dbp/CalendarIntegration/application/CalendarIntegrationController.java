package com.example.proyecto_dbp.CalendarIntegration.application;

import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegrationService;
import com.example.proyecto_dbp.CalendarIntegration.dto.CalendarIntegrationDTO;
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
    public List<CalendarIntegrationDTO> getAllCalendarIntegrations() {
        return calendarIntegrationService.getAllCalendarIntegrations();
    }

    @PostMapping
    public ResponseEntity<CalendarIntegrationDTO> createCalendarIntegration(@RequestBody CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegrationDTO createdCalendarIntegration = calendarIntegrationService.createCalendarIntegration(calendarIntegrationDTO);
        return new ResponseEntity<>(createdCalendarIntegration, HttpStatus.CREATED);
    }

    // Other CRUD operations
}

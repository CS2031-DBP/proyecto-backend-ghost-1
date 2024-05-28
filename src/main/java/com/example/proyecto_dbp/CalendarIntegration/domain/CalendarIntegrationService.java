package com.example.proyecto_dbp.CalendarIntegration.domain;

import com.example.proyecto_dbp.CalendarIntegration.infrastructure.CalendarIntegrationRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalendarIntegrationService {

    @Autowired
    private CalendarIntegrationRepository calendarIntegrationRepository;

    public List<CalendarIntegration> getAllCalendarIntegrations() {return calendarIntegrationRepository.findAll();}

    public CalendarIntegration getCalendarIntegrationByActivityId(Long id) {
        return calendarIntegrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarIntegration not found with id " + id));
    }

    public CalendarIntegration createCalendarIntegration(CalendarIntegration calendarIntegration) {
        return calendarIntegrationRepository.save(calendarIntegration);
    }

    public CalendarIntegration updateCalendarIntegration(Long id, CalendarIntegration updatedCalendarIntegration) {
        CalendarIntegration calendarIntegration = calendarIntegrationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarIntegration not found with id " + id));

        calendarIntegration.setGoogleCalendarEventId(updatedCalendarIntegration.getGoogleCalendarEventId());
        return calendarIntegrationRepository.save(calendarIntegration);
    }

    public void deleteCalendarIntegration(Long id) {
        if (!calendarIntegrationRepository.existsById(id)) {
            throw new ResourceNotFoundException("CalendarIntegration not found with id " + id);
        }
        calendarIntegrationRepository.deleteById(id);
    }
}

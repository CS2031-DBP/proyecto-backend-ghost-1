package com.example.proyecto_dbp.CalendarIntegration.domain;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.CalendarIntegration.infrastructure.CalendarIntegrationRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CalendarIntegrationService {

    @Autowired
    private CalendarIntegrationRepository calendarIntegrationRepository;

    public List<CalendarIntegration> getAllCalendarIntegrations() {
        return calendarIntegrationRepository.findAll();
    }

    public CalendarIntegration getCalendarIntegrationByActivityId(Long activityId) {
        return calendarIntegrationRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarIntegration not found with id " + activityId));
    }

    public CalendarIntegration createCalendarIntegration(CalendarIntegration calendarIntegration) {
        return calendarIntegrationRepository.save(calendarIntegration);
    }

    public CalendarIntegration updateCalendarIntegration(Long activityId, CalendarIntegration updatedCalendarIntegration) {
        CalendarIntegration calendarIntegration = calendarIntegrationRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarIntegration not found with id " + activityId));

        calendarIntegration.setGoogleCalendarEventId(updatedCalendarIntegration.getGoogleCalendarEventId());
        calendarIntegration.setSynchronizationStatus(updatedCalendarIntegration.getSynchronizationStatus());

        return calendarIntegrationRepository.save(calendarIntegration);
    }

    public void deleteCalendarIntegration(Long activityId) {
        if (!calendarIntegrationRepository.existsById(activityId)) throw new ResourceNotFoundException("CalendarIntegration not found with id " + activityId);
        calendarIntegrationRepository.deleteById(activityId);
    }
}

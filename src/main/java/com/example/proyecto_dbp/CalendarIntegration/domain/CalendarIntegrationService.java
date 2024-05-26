package com.example.proyecto_dbp.CalendarIntegration.domain;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.CalendarIntegration.dto.CalendarIntegrationDTO;
import com.example.proyecto_dbp.CalendarIntegration.infrastructure.CalendarIntegrationRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CalendarIntegrationService {

    @Autowired
    private CalendarIntegrationRepository calendarIntegrationRepository;

    @Autowired
    private ActivityRepository activityRepository;

    public List<CalendarIntegrationDTO> getAllCalendarIntegrations() {
        return calendarIntegrationRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CalendarIntegrationDTO getCalendarIntegrationByActivityId(Long activityId) {
        CalendarIntegration calendarIntegration = calendarIntegrationRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarIntegration not found with id " + activityId));
        return convertToDTO(calendarIntegration);
    }

    public CalendarIntegrationDTO createCalendarIntegration(CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegration calendarIntegration = convertToEntity(calendarIntegrationDTO);
        calendarIntegration = calendarIntegrationRepository.save(calendarIntegration);
        return convertToDTO(calendarIntegration);
    }

    public CalendarIntegrationDTO updateCalendarIntegration(Long activityId, CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegration calendarIntegration = calendarIntegrationRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("CalendarIntegration not found with id " + activityId));
        calendarIntegration.setGoogleCalendarEventId(calendarIntegrationDTO.getGoogleCalendarEventId());
        calendarIntegration.setSynchronizationStatus(calendarIntegrationDTO.getSynchronizationStatus());
        calendarIntegration = calendarIntegrationRepository.save(calendarIntegration);
        return convertToDTO(calendarIntegration);
    }

    public void deleteCalendarIntegration(Long activityId) {
        calendarIntegrationRepository.deleteById(activityId);
    }

    private CalendarIntegrationDTO convertToDTO(CalendarIntegration calendarIntegration) {
        return CalendarIntegrationDTO.builder()
                .activityId(calendarIntegration.getActivityId())
                .googleCalendarEventId(calendarIntegration.getGoogleCalendarEventId())
                .synchronizationStatus(calendarIntegration.getSynchronizationStatus())
                .build();
    }

    private CalendarIntegration convertToEntity(CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegration calendarIntegration = new CalendarIntegration();
        calendarIntegration.setActivityId(calendarIntegrationDTO.getActivityId());
        calendarIntegration.setGoogleCalendarEventId(calendarIntegrationDTO.getGoogleCalendarEventId());
        calendarIntegration.setSynchronizationStatus(calendarIntegrationDTO.getSynchronizationStatus());
        activityRepository.findById(calendarIntegrationDTO.getActivityId()).ifPresent(calendarIntegration::setActivity);
        return calendarIntegration;
    }
}

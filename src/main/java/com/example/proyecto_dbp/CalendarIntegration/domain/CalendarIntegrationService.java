package com.example.proyecto_dbp.CalendarIntegration.domain;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.CalendarIntegration.dto.CalendarIntegrationDTO;
import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegration;

import com.example.proyecto_dbp.CalendarIntegration.infrastructure.CalendarIntegrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public CalendarIntegrationDTO createCalendarIntegration(CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegration calendarIntegration = convertToEntity(calendarIntegrationDTO);
        calendarIntegration = calendarIntegrationRepository.save(calendarIntegration);
        return convertToDTO(calendarIntegration);
    }

    // Other service methods

    private CalendarIntegrationDTO convertToDTO(CalendarIntegration calendarIntegration) {
        CalendarIntegrationDTO calendarIntegrationDTO = new CalendarIntegrationDTO();
        calendarIntegrationDTO.setActivityId(calendarIntegration.getActivityId());
        calendarIntegrationDTO.setGoogleCalendarEventId(calendarIntegration.getGoogleCalendarEventId());
        calendarIntegrationDTO.setEstadoSincronizacion(calendarIntegration.getEstadoSincronizacion());
        return calendarIntegrationDTO;
    }

    private CalendarIntegration convertToEntity(CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegration calendarIntegration = new CalendarIntegration();
        calendarIntegration.setActivityId(calendarIntegrationDTO.getActivityId());
        calendarIntegration.setGoogleCalendarEventId(calendarIntegrationDTO.getGoogleCalendarEventId());
        calendarIntegration.setEstadoSincronizacion(calendarIntegrationDTO.getEstadoSincronizacion());
        activityRepository.findById(calendarIntegrationDTO.getActivityId()).ifPresent(calendarIntegration::setActivity);
        return calendarIntegration;
    }
}

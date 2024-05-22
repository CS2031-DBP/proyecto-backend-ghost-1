package com.example.proyecto_dbp.CalendarIntegration.domain;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.CalendarIntegration.dto.CalendarIntegrationDTO;
import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegration;

import com.example.proyecto_dbp.CalendarIntegration.infrastructure.CalendarIntegrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<CalendarIntegrationDTO> getCalendarIntegrationByActivityId(Long activityId) {
        return calendarIntegrationRepository.findById(activityId).map(this::convertToDTO);
    }

    public CalendarIntegrationDTO createCalendarIntegration(CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegration calendarIntegration = convertToEntity(calendarIntegrationDTO);
        calendarIntegration = calendarIntegrationRepository.save(calendarIntegration);
        return convertToDTO(calendarIntegration);
    }

    public CalendarIntegrationDTO updateCalendarIntegration(Long activityId, CalendarIntegrationDTO calendarIntegrationDTO) {
        Optional<CalendarIntegration> optionalCalendarIntegration = calendarIntegrationRepository.findById(activityId);
        if (optionalCalendarIntegration.isPresent()) {
            CalendarIntegration calendarIntegration = optionalCalendarIntegration.get();
            calendarIntegration.setGoogleCalendarEventId(calendarIntegrationDTO.getGoogleCalendarEventId());
            calendarIntegration.setSynchronizationStatus(calendarIntegrationDTO.getEstadoSincronizacion());
            calendarIntegration = calendarIntegrationRepository.save(calendarIntegration);
            return convertToDTO(calendarIntegration);
        }
        return null;
    }

    public void deleteCalendarIntegration(Long activityId) {
        calendarIntegrationRepository.deleteById(activityId);
    }

    private CalendarIntegrationDTO convertToDTO(CalendarIntegration calendarIntegration) {
        CalendarIntegrationDTO calendarIntegrationDTO = new CalendarIntegrationDTO();
        calendarIntegrationDTO.setActivityId(calendarIntegration.getActivityId());
        calendarIntegrationDTO.setGoogleCalendarEventId(calendarIntegration.getGoogleCalendarEventId());
        calendarIntegrationDTO.setEstadoSincronizacion(calendarIntegration.getSynchronizationStatus());
        return calendarIntegrationDTO;
    }

    private CalendarIntegration convertToEntity(CalendarIntegrationDTO calendarIntegrationDTO) {
        CalendarIntegration calendarIntegration = new CalendarIntegration();
        calendarIntegration.setActivityId(calendarIntegrationDTO.getActivityId());
        calendarIntegration.setGoogleCalendarEventId(calendarIntegrationDTO.getGoogleCalendarEventId());
        calendarIntegration.setSynchronizationStatus(calendarIntegrationDTO.getEstadoSincronizacion());
        activityRepository.findById(calendarIntegrationDTO.getActivityId()).ifPresent(calendarIntegration::setActivity);
        return calendarIntegration;
    }
}

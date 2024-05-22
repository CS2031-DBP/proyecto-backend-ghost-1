package com.example.proyecto_dbp.CalendarIntegration.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CalendarIntegrationDTO {

    //Las ids entran en un DTO?
    @NotNull
    private Long activityId;
    private String googleCalendarEventId;
    @NotNull
    private String synchronizationStatus;

    // Getters and Setters

/*
    public Long getActivityId() {return activityId;}

    public void setActivityId(Long activityId) {this.activityId = activityId;}

    public String getGoogleCalendarEventId() {return googleCalendarEventId;}

    public void setGoogleCalendarEventId(String googleCalendarEventId) {this.googleCalendarEventId = googleCalendarEventId;}

    public String getEstadoSincronizacion() {return synchronizationStatus;}

    public void setEstadoSincronizacion(String estadoSincronizacion) {this.synchronizationStatus = synchronizationStatus;}

 */
}

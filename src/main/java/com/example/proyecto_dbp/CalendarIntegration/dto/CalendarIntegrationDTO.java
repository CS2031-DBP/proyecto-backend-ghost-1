package com.example.proyecto_dbp.CalendarIntegration.dto;

public class CalendarIntegrationDTO {
    private Long activityId;
    private String googleCalendarEventId;
    private String estadoSincronizacion;

    // Getters and Setters


    public Long getActivityId() {return activityId;}

    public void setActivityId(Long activityId) {this.activityId = activityId;}

    public String getGoogleCalendarEventId() {return googleCalendarEventId;}

    public void setGoogleCalendarEventId(String googleCalendarEventId) {this.googleCalendarEventId = googleCalendarEventId;}

    public String getEstadoSincronizacion() {return estadoSincronizacion;}

    public void setEstadoSincronizacion(String estadoSincronizacion) {this.estadoSincronizacion = estadoSincronizacion;}
}

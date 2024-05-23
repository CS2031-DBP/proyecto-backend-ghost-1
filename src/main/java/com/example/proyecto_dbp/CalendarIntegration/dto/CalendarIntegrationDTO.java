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

}

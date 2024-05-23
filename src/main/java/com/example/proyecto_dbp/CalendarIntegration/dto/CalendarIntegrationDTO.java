package com.example.proyecto_dbp.CalendarIntegration.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CalendarIntegrationDTO {

    @NotNull
    private Long activityId;

    private String googleCalendarEventId;

    @NotNull
    private String synchronizationStatus;
}

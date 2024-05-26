package com.example.proyecto_dbp.CalendarIntegration.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalendarIntegrationDTO {

    @NotNull
    private Long activityId;

    private String googleCalendarEventId;

    @NotNull
    private String synchronizationStatus;
}

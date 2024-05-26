package com.example.proyecto_dbp.Event.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class EventDTO extends ActivityDTO {

    @NotNull
    private String location;

    @NotNull
    private Boolean allDay;

    @NotNull
    private String organizer;

    @NotNull
    private String attendees;

    @NotNull
    private String reminder;

}

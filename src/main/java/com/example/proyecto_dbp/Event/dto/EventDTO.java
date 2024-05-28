package com.example.proyecto_dbp.Event.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
public class EventDTO extends ActivityDTO {
    private String location;
    private Boolean allDay;
    private String organizer;
    private String attendees;
    private String reminder;

}

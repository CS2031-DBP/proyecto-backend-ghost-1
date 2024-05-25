package com.example.proyecto_dbp.Event.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
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

    public static EventDTOBuilder builder() {return new EventDTOBuilder();}

    public static class EventDTOBuilder extends ActivityDTO.ActivityDTOBuilder<EventDTO, EventDTOBuilder> {
        public EventDTOBuilder() {super();}

        @Override
        protected EventDTOBuilder self() {
            return null;
        }

        @Override
        public EventDTO build() {
            return null;
        }
    }
}

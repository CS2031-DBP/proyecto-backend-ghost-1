package com.example.proyecto_dbp.Event.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class EventDTO extends ActivityDTO {

    @NonNull
    @Size(min = 1, max = 255)
    private String location;

    @NonNull
    private boolean allDay;

    @NonNull
    @Size(min = 1, max = 255)
    private String organizer;

    @NonNull
    @Size(min = 1, max = 255)
    private String attendees;

    @NonNull
    @Size(min = 1, max = 255)
    private String reminder;

    @Builder(builderMethodName = "eventBuilder")
    public EventDTO(@NonNull Long id, @NonNull @Size(min = 1, max = 255) String titulo, @NonNull Date fechaInicio, @NonNull Date fechaFin, @NonNull String estado, @NonNull String courseId, @NonNull Long descripcion, @NonNull String location, boolean allDay, @NonNull String organizer, @NonNull String attendees, @NonNull String reminder) {
        super(id, titulo, fechaInicio, fechaFin, estado, courseId, descripcion);
        this.location = location;
        this.allDay = allDay;
        this.organizer = organizer;
        this.attendees = attendees;
        this.reminder = reminder;
    }

    public static class EventDTOBuilder {
        public EventDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
    }
}

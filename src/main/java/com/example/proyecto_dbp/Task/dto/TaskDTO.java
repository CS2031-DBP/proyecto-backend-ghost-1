package com.example.proyecto_dbp.Task.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import com.example.proyecto_dbp.Event.dto.EventDTO;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
public class TaskDTO extends ActivityDTO {

    @NonNull
    @Size(min = 1, max = 255)
    private String priority;

    @NonNull
    private Boolean completed;

    @Builder
    public TaskDTO(@NonNull Long id, @NonNull @Size(min = 1, max = 255) String titulo, @NonNull Date fechaInicio, @NonNull Date fechaFin, @NonNull String estado, @NonNull String courseId, @NonNull Long descripcion, @NonNull String priority, @NonNull Boolean completed) {
        super(id, titulo, fechaInicio, fechaFin, estado, courseId, descripcion);
        this.priority = priority;
        this.completed = completed;
    }

    public static class TaskDTOBuilder {
        public TaskDTO.TaskDTOBuilder id(Long id) {
            this.id = id;
            return this;
        }
    }

}

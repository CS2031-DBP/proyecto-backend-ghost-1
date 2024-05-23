package com.example.proyecto_dbp.Task.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskDTO extends ActivityDTO {

    @NonNull
    private Long id;

    @NonNull
    @Size(min = 1, max = 255)
    private String titulo;

    @NonNull
    private Date fechaInicio;

    @NonNull
    private Date fechaFin;

    @NonNull
    private String estado;

    @NonNull
    private Long courseId;

    @NonNull
    private String descripcion;

    @NonNull
    @Size(min = 1, max = 255)
    private String priority;

    @NonNull
    private Boolean completed;
}

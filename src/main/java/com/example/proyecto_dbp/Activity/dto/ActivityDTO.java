package com.example.proyecto_dbp.Activity.dto;

import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDTO {

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
    @Size(min = 1, max = 255)
    private String estado;

    @NonNull
    @Size(min = 1, max = 255)
    private String descripcion;

    @NonNull
    private Long courseId;
}

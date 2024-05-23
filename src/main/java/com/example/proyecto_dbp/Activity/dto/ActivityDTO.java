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

    @Builder
    public ActivityDTO(@NonNull Long id, @NonNull @Size(min = 1, max = 255) String titulo, @NonNull Date fechaInicio, @NonNull Date fechaFin, @NonNull String estado, @NonNull Long courseId, @NonNull String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.descripcion = descripcion;
        this.courseId = courseId;
    }
}

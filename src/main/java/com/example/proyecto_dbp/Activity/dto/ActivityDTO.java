package com.example.proyecto_dbp.Activity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;

@Data
public class ActivityDTO {
    @NonNull
    private Long id;

    @NonNull
    @Size(min = 1, max = 255)
    private String titulo;

    @Size(min = 1, max = 255)
    private String descripcion;

    @NotNull
    private Date fechaInicio;

    @NonNull
    private Date fechaFin;

    @NotNull
    private String estado;
    private Long courseId;

}

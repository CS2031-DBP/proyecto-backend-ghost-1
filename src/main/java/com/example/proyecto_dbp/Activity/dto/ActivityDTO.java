package com.example.proyecto_dbp.Activity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
public class ActivityDTO {

    @NotNull
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String titulo;

    @NotNull
    private Date fechaInicio;

    @NotNull
    private Date fechaFin;

    @NotNull
    private String estado;

    @NotNull
    private Long CourseId;

    @NotNull
    private String descripcion;
}

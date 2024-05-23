package com.example.proyecto_dbp.Course.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDTO {
    @NonNull
    private Long id;

    @NonNull
    @Size(min = 1, max = 30)
    private String nombreCurso;

    @NotNull
    @Size(min = 1, max = 255)
    private String descripcion;

    @NotNull
    @Size(min = 2, max = 50)
    private String profesor;

    @NotNull
    private Long userId;
}

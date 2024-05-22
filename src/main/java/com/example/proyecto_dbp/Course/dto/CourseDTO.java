package com.example.proyecto_dbp.Course.dto;


import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NonNull;

@Data
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

    // Getters and Setters

    /*
    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getNombreCurso() {return nombreCurso;}

    public void setNombreCurso(String nombreCurso) {this.nombreCurso = nombreCurso;}

    public String getDescripcion() {return descripcion;}

    public void setDescripcion(String descripcion) {this.descripcion = descripcion;}

    public String getProfesor() {return profesor;}

    public void setProfesor(String profesor) {this.profesor = profesor;}

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {this.userId = userId;}

     */
}

package com.example.proyecto_dbp.Course.dto;

public class CourseDTO {
    private Long id;
    private String nombreCurso;
    private String descripcion;
    private String profesor;
    private Long userId;

    // Getters and Setters


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
}

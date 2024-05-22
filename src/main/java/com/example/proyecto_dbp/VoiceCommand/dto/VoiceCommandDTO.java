package com.example.proyecto_dbp.VoiceCommand.dto;


import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NonNull;

@Data
public class VoiceCommandDTO {
    @NonNull
    private Long id;
    @NonNull
    private String comando;
    @NonNull
    @Size(min = 5, max = 255)
    private String descripcionAccion;
    private Long userId;
    private Long activityId;

    // Getters and Setters

/*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getComando() {
        return comando;
    }

    public void setComando(String comando) {
        this.comando = comando;
    }

    public String getDescripcionAccion() {
        return descripcionAccion;
    }

    public void setDescripcionAccion(String descripcionAccion) {
        this.descripcionAccion = descripcionAccion;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getActivityId() {
        return activityId;
    }

    public void setActivityId(Long activityId) {
        this.activityId = activityId;
    }

 */
}

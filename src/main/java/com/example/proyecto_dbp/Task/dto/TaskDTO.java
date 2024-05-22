package com.example.proyecto_dbp.Task.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

import java.util.Date;

public class TaskDTO extends ActivityDTO {

    @NonNull
    @Size(min = 1, max = 255)
    private String priority;

    @NonNull
    private Boolean completed;

    //Ni idea de estos constructores XD, me los botó el IntelliJ
    public TaskDTO(@NonNull Long id, @NonNull @Size(min = 1, max = 255) String titulo, @NonNull Date fechaFin) {
        super(id, titulo, fechaFin);
    }

    // Getters and Setters
    /*
    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

     */
}

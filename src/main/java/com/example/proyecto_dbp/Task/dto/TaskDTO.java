package com.example.proyecto_dbp.Task.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;

public class TaskDTO extends ActivityDTO {
    private String priority;
    private Boolean completed;

    // Getters and Setters

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
}

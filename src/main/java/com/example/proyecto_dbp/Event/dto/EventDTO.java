package com.example.proyecto_dbp.Event.dto;

import com.example.proyecto_dbp.Activity.dto.ActivityDTO;
import jakarta.validation.constraints.Size;
import lombok.NonNull;

import java.util.Date;

public class EventDTO extends ActivityDTO {
    //Ni idea de estos constructores XD, me los botó el IntelliJ
    public EventDTO(@NonNull Long id, @NonNull @Size(min = 1, max = 255) String titulo, @NonNull Date fechaFin) {
        super(id, titulo, fechaFin);
    }
    // Additional fields specific to Event can go here

    // Getters and Setters
}

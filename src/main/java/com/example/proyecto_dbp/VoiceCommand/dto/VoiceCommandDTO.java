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

}

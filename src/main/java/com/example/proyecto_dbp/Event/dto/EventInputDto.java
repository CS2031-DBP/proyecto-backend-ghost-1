package com.example.proyecto_dbp.Event.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventInputDto {
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
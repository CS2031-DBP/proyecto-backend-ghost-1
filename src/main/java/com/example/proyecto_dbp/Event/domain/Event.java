package com.example.proyecto_dbp.Event.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "events")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column
    private LocalDateTime startTime;

    @Column
    private LocalDateTime endTime;

    // Getters and Setters
}

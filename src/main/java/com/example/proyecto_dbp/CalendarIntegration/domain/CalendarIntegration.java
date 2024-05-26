package com.example.proyecto_dbp.CalendarIntegration.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "calendar_integrations")
@Setter
@Getter
public class CalendarIntegration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long activityId;

    @Column(nullable = false)
    private String googleCalendarEventId;

    @Column(nullable = false)
    private String synchronizationStatus;

    @OneToOne
    @JoinColumn(name = "activity_id", referencedColumnName = "id")
    private Activity activity;
}

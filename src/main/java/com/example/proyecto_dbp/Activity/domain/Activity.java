package com.example.proyecto_dbp.Activity.domain;

import com.example.proyecto_dbp.Course.domain.Course;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "activities")
@Inheritance(strategy = InheritanceType.JOINED)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startTime;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endTime;

    @Column(nullable = false)
    private String status;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

}
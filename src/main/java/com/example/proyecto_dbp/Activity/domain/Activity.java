package com.example.proyecto_dbp.Activity.domain;

import com.example.proyecto_dbp.Course.domain.Course;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Date;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Entity
@Table(name = "activities")
@Inheritance(strategy = InheritanceType.JOINED)
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;

    @Column(nullable = false)
    private String estado;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;
}

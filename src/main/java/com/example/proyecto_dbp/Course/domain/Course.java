package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.User.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "courses")
@Data
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Courseid;

    @Column(nullable = false)
    private String nombreCurso;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String profesor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.Event.domain.Event;
import com.example.proyecto_dbp.User.domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "courses")
@Setter
@Getter
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombreCurso;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false)
    private String profesor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "course")
    private List<Event> events;
}

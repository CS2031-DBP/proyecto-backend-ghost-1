package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.User.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String professor;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "course")
    private List<Activity> activities;



    // Constructor, getters y setters

}

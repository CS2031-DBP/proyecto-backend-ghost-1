package com.example.proyecto_dbp.Task.domain;


import com.example.proyecto_dbp.User.domain.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDate dueDate;
    private String status;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}

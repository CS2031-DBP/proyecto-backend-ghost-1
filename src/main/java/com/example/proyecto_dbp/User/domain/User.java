package com.example.proyecto_dbp.User.domain;

import com.example.proyecto_dbp.Course.domain.Course;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "user")
    private List<Course> courses;

    @OneToMany(mappedBy = "user")
    private List<VoiceCommand> voiceCommands;

}

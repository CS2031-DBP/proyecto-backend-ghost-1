package com.example.proyecto_dbp.VoiceCommand.domain;

import com.example.proyecto_dbp.User.domain.User;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Table;

//import javax.persistence.*;

@Entity
@Table(name = "voice_commands")
public class VoiceCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String command;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User usuario;

    // Getters y setters
}


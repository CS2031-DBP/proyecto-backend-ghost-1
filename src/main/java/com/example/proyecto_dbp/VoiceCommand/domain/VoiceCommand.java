package com.example.proyecto_dbp.VoiceCommand.domain;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.User.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Table(name = "voice_commands")
public class VoiceCommand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String command;

    @Column(nullable = false)
    private String descriptionAction;

    @Column(nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private Activity activity;
}

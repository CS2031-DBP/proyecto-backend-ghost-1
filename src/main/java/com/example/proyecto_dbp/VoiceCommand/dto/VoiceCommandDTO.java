package com.example.proyecto_dbp.VoiceCommand.dto;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.User.domain.User;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class VoiceCommandDTO {
    @NotNull
    private Long id;

    @NotNull
    private String command;

    @NotNull
    @Size(min = 5, max = 255)
    private String descriptionAction;

    private LocalDateTime timestamp;

    @NotNull
    private User user;

    @NotNull
    private Activity activity;
}

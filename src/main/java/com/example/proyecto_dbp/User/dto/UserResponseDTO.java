package com.example.proyecto_dbp.User.dto;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserResponseDTO {
    @NonNull
    private Long id;
    @NonNull
    private String name;
    @NonNull
    private String email;
}

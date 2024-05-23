package com.example.proyecto_dbp.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class UserDTO {

    @NonNull
    private Long id;

    @NonNull
    @Email
    private String email;

    @NonNull
    @Size(min = 1, max = 255)
    private String name;

}

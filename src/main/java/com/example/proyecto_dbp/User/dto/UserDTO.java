package com.example.proyecto_dbp.User.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    @NotNull
    private Long id;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 1, max = 255)
    private String name;

    @NotNull
    @Size(min = 8, max = 64)
    private String password;

    @NotNull
    private String role;
}

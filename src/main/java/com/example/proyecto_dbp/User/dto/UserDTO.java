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

    /*
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
     */
}

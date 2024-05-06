package com.example.proyecto_dbp.User.dto;

import jakarta.validation.constraints.Email;

public class UserInputDto {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    // Getters and setters
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}

    public void setEmail(String email) {this.email = email;}
    public void setName(String name) {this.name = name;}
    public void setPassword(String password) {this.password = password;}
}


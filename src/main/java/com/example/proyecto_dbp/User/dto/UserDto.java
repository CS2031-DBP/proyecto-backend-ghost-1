package com.example.proyecto_dbp.User.dto;


import jakarta.validation.constraints.*;

public class UserDto {
    private Long id;

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    public UserDto() {}

    public void setId(Long id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setEmail(String email) {this.email = email;}

    public String getName() {return name;}
    public Long getId() {return id;}
    public String getEmail() {return email;}
}
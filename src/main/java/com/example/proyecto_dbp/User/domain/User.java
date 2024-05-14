package com.example.proyecto_dbp.User.domain;

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

    // Constructor, getters y setters

    public User(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public User() {}

    public Long getId() {return id;}
    public String getPassword() {return password;}
    public String getEmail() {return email;}

    public void setId(Long id) {this.id = id;}
    public void setPassword(String password) {this.password = password;
    public void setEmail(String email) {this.email = email;}
}
}

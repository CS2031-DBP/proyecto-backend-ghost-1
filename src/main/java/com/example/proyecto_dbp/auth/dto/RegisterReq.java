package com.example.proyecto_dbp.auth.dto;

import lombok.Data;

@Data
public class RegisterReq {
    private String name;
    private String email;
    private String password;
}
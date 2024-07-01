package com.example.proyecto_dbp.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@Data
public class LoginReq {
    private String email;
    private String password;
}
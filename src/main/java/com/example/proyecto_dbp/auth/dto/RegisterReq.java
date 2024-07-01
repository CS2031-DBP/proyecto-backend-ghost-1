package com.example.proyecto_dbp.auth.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Data
public class RegisterReq {
    private String firstName;
    private String email;
    private String password;

}
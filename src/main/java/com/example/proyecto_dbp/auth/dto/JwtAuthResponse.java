package com.example.proyecto_dbp.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data @Getter
public class JwtAuthResponse {
    private String token;
}
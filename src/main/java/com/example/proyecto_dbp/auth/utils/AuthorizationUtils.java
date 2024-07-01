package com.example.proyecto_dbp.auth.utils;


import com.example.proyecto_dbp.config.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationUtils {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    public JwtAuthenticationFilter authenticationJwtTokenFilter() {
        return jwtAuthenticationFilter;
    }
}

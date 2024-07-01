package com.example.proyecto_dbp.auth.application;

import com.example.proyecto_dbp.auth.domain.AuthService;
import com.example.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.example.proyecto_dbp.auth.dto.LoginReq;
import com.example.proyecto_dbp.auth.dto.RegisterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public JwtAuthResponse registerUser(@RequestBody RegisterReq registerReq) {
        return authService.register(registerReq);
    }

    @PostMapping("/login")
    public JwtAuthResponse loginUser(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }
}

package com.example.proyecto_dbp.auth.application;

import com.example.proyecto_dbp.auth.domain.AuthService;
import com.example.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.example.proyecto_dbp.auth.dto.LoginReq;
import com.example.proyecto_dbp.auth.dto.RegisterReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    public AuthController(AuthService authService) {this.authService = authService;}

    @GetMapping("/hello")
    public String hello() {
        return "La nube funciona";
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterReq registerReq) {
        authService.register(registerReq);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public JwtAuthResponse loginUser(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }
}

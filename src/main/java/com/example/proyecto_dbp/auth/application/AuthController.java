package com.example.proyecto_dbp.auth.application;

import com.example.proyecto_dbp.auth.domain.AuthService;
import com.example.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.example.proyecto_dbp.auth.dto.LoginReq;
import com.example.proyecto_dbp.auth.dto.RegisterReq;
import com.example.proyecto_dbp.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/hello")
    public String hello() {return "Hello";}

    @GetMapping("/bye")
    public String bye() {return "Bye";}

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterReq registerReq) {
        try {
            return ResponseEntity.ok(authService.registerUser(registerReq));
        } catch (UserAlreadyExistException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public JwtAuthResponse loginUser(@RequestBody LoginReq loginReq) {
        return authService.login(loginReq);
    }
}

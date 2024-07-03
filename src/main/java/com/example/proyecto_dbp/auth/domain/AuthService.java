package com.example.proyecto_dbp.auth.domain;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.example.proyecto_dbp.auth.dto.LoginReq;
import com.example.proyecto_dbp.auth.dto.RegisterReq;
import com.example.proyecto_dbp.config.JwtService;
import com.example.proyecto_dbp.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public String registerUser(RegisterReq registerReq) {
        try {
            if (userRepository.existsByEmail(registerReq.getEmail())) {
                throw new UserAlreadyExistException("User already exists with email " + registerReq.getEmail());
            }

            User user = new User();
            user.setEmail(registerReq.getEmail());
            user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
            user.setName(registerReq.getName());

            userRepository.save(user);

            return "User registered successfully";
        } catch (DataIntegrityViolationException e) {
            throw new UserAlreadyExistException("User already exists with email " + registerReq.getEmail());
        }
    }

    public JwtAuthResponse login(LoginReq req) {
        Optional<User> user;
        user = userRepository.findByEmail(req.getEmail());

        if (user.isEmpty()) throw new UsernameNotFoundException("Email is not registered");

        if (!passwordEncoder.matches(req.getPassword(), user.get().getPassword()))
            throw new IllegalArgumentException("Password is incorrect");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(user.get()));
        return response;
    }
}

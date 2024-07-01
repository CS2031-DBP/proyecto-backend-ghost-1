package com.example.proyecto_dbp.auth.domain;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.example.proyecto_dbp.auth.dto.LoginReq;
import com.example.proyecto_dbp.auth.dto.RegisterReq;
import com.example.proyecto_dbp.config.JwtService;
import com.example.proyecto_dbp.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public JwtAuthResponse register(RegisterReq req) {
        Optional<User> user = userRepository.findByEmail(req.getEmail());
        if (user.isPresent()) throw new UserAlreadyExistException("Email is already registered");

        User newUser = new User();
        newUser.setPassword(passwordEncoder.encode(req.getPassword()));
        newUser.setEmail(req.getEmail());
        newUser.setName(req.getName());
        userRepository.save(newUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(newUser.getEmail(), req.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(authentication));
        return response;
    }

    public JwtAuthResponse login(LoginReq req) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        JwtAuthResponse response = new JwtAuthResponse();
        response.setToken(jwtService.generateToken(authentication));
        return response;
    }
}

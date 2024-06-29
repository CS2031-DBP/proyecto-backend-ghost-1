package com.example.proyecto_dbp.auth.domain;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.example.proyecto_dbp.auth.dto.LoginReq;
import com.example.proyecto_dbp.auth.dto.RegisterReq;
import com.example.proyecto_dbp.auth.exceptions.UserAlreadyExistException;
import com.example.proyecto_dbp.config.JwtService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;
    @Autowired
    public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper =new ModelMapper();
    }

    public JwtAuthResponse login(LoginReq req) {
        Optional<User> users = userRepository.findByEmail(req.getEmail());

        if (users.isEmpty()) throw new UsernameNotFoundException("Email no registrado");

        if (!passwordEncoder.matches(req.getPassword(), users.get().getPassword()))
            throw new IllegalArgumentException("Contraseña incorrecta");

        JwtAuthResponse response = new JwtAuthResponse();

        response.setToken(jwtService.generateToken(users.get()));
        return response;
    }


    public JwtAuthResponse register(RegisterReq registerReq) {
        if (userRepository.existsByEmail(registerReq.getEmail())) {
            throw new UserAlreadyExistException("User with email " + registerReq.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(registerReq.getEmail());
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));
        user.setId(user.getId());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return new JwtAuthResponse(token);
    }


}

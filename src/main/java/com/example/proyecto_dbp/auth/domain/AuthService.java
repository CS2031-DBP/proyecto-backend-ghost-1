package com.example.proyecto_dbp.auth.domain;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.dto.UserDTO;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.auth.dto.JwtAuthResponse;
import com.example.proyecto_dbp.auth.dto.LoginReq;
import com.example.proyecto_dbp.auth.dto.RegisterReq;
import com.example.proyecto_dbp.auth.exceptions.UserAlreadyExistException;
import com.example.proyecto_dbp.config.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public JwtAuthResponse login(LoginReq loginReq) {
        User user = userRepository.findByEmail(loginReq.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginReq.getEmail()));

        if (passwordEncoder.matches(loginReq.getPassword(), user.getPassword())) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(user.getId());
            userDTO.setEmail(user.getEmail());
            userDTO.setPassword(user.getPassword());

            String token = jwtService.generateToken(userDTO);
            return new JwtAuthResponse(token);
        } else throw new BadCredentialsException("Invalid password");
    }

    public JwtAuthResponse register(RegisterReq registerReq) {
        if (userRepository.existsByEmail(registerReq.getEmail())) {
            throw new UserAlreadyExistException("User with email " + registerReq.getEmail() + " already exists");
        }

        User user = new User();
        user.setEmail(registerReq.getEmail());
        user.setPassword(passwordEncoder.encode(registerReq.getPassword()));

        userRepository.save(user);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());

        String token = jwtService.generateToken(userDTO);
        return new JwtAuthResponse(token);
    }
}

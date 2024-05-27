package com.example.proyecto_dbp.User.domain;

import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import com.example.proyecto_dbp.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {this.userRepository = userRepository;}

    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .map(user -> (org.springframework.security.core.userdetails.UserDetails) user)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public List<User> getAllUsers() {return userRepository.findAll();}

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
    }

    public User createUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exists with email " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        userRepository.deleteById(id);
    }
}

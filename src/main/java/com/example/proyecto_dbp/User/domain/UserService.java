package com.example.proyecto_dbp.User.domain;

import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.auth.utils.AuthorizationUtils;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import com.example.proyecto_dbp.exceptions.UnauthorizeOperationException;
import com.example.proyecto_dbp.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final AuthorizationUtils authorizationUtils;

    @Autowired
    public UserService(UserRepository userRepository, AuthorizationUtils authorizationUtils) {
        this.userRepository = userRepository;
        this.authorizationUtils = authorizationUtils;
    }

    public UserDetailsService userDetailsService() {
        return email -> userRepository.findByEmail(email)
                .map(user -> (org.springframework.security.core.userdetails.UserDetails) user)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public List<User> getAllUsers() {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username == null) throw new UnauthorizeOperationException("Anonymous User not allowed to access this resource");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        if (!authorizationUtils.isAdminOrResourceOwner(user)) {
            throw new UnauthorizeOperationException("User has no permission to view this user");
        }
        return user;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + email));
    }

    public User createUser(User user) {
        String username = authorizationUtils.getCurrentUserEmail();
        if(username==null) throw new UnauthorizeOperationException("Anonymous User not allowed to access this resource");
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new UserAlreadyExistException("User already exists with email " + user.getEmail());
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));
        if (!authorizationUtils.isAdminOrResourceOwner(user)) {
            throw new UnauthorizeOperationException("User has no permission to modify this user");
        }
        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        user.setPassword(updatedUser.getPassword());
        user.setRoles(updatedUser.getRoles());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id " + id));

        if (!authorizationUtils.isAdminOrResourceOwner(user)) {
            throw new UnauthorizeOperationException("User has no permission to delete this user");
        }
        userRepository.deleteById(id);
    }

}

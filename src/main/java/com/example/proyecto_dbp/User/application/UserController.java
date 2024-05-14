package com.example.proyecto_dbp.User.application;

import com.example.proyecto_dbp.User.domain.UserService;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.proyecto_dbp.User.domain.User;

import java.util.*;

@RestController
@RequestMapping("/api/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id]")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        boolean deleted = userService.deleteUserById(id);
        if(deleted) return ResponseEntity.noContent().build();
        else return ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userRepository.getUserById(id);
        if (user != null) return ResponseEntity.ok(user);
        else return ResponseEntity.notFound().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestParam String name, @RequestParam String email, @RequestParam String password) {
        User updatedUser = userService.updateUser(id, name, email, password);
        if (updatedUser != null) return ResponseEntity.ok(updatedUser);
        else return ResponseEntity.notFound().build();
    }

}

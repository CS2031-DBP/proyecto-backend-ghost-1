package com.example.proyecto_dbp.User.domain;

import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        return userRepository.save(user);
    }
    public void deleteUserById(Long id) {userRepository.deleteById(id);}
    public List<User> getUsers() {return userRepository.findAll();}


    public User updateUser(Long id, String name, String email, String password){
        User user = userRepository.findById(id).orElse(null);

    }
}

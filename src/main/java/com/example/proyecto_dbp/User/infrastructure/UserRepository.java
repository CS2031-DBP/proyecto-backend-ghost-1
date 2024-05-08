package com.example.proyecto_dbp.User.infrastructure;

import com.example.proyecto_dbp.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}

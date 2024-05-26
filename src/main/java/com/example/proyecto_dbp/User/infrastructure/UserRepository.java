package com.example.proyecto_dbp.User.infrastructure;

import com.example.proyecto_dbp.User.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@EnableJpaRepositories(basePackages = "com.example.proyecto_dbp")
@Repository
public interface UserRepository <T extends User> extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<T> findByEmail(String email);
}

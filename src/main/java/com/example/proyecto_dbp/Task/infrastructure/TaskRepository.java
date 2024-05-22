package com.example.proyecto_dbp.Task.infrastructure;


import com.example.proyecto_dbp.Task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}

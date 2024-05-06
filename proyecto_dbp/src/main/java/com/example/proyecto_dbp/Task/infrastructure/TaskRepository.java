package com.example.proyecto_dbp.Task.infrastructure;

import com.example.proyecto_dbp.Task.domain.Task;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {

}

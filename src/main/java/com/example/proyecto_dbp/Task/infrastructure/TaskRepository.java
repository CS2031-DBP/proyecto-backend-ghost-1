package com.example.proyecto_dbp.Task.infrastructure;

import com.example.proyecto_dbp.Task.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByCourseId(Long courseId);
}

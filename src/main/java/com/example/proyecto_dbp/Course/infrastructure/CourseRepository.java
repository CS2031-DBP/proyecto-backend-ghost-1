package com.example.proyecto_dbp.Course.infrastructure;

import com.example.proyecto_dbp.Course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByUserId(Long userId);
}

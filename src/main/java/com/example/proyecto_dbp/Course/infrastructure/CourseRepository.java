package com.example.proyecto_dbp.Course.infrastructure;

import com.example.proyecto_dbp.Course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}

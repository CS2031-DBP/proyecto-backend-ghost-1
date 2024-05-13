package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.Course.dto.CourseDto;
import com.example.proyecto_dbp.Course.dto.CourseInputDto;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Transactional
    public CourseDto createCourse(CourseInputDto inputDto) {
        Course course = new Course();
        course.setName(inputDto.getName());
        course = courseRepository.save(course);
        return new CourseDto(course.getId(), course.getName());
    }

    @Transactional(readOnly = true)
    public CourseDto findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        return new CourseDto(course.getId(), course.getName());
    }
}
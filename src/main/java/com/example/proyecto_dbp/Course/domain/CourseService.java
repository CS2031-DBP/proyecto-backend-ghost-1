package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses() {return courseRepository.findAll();}

    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
    }

    public List<Course> getCoursesByUserId(Long userId) {
        List<Course> courses = courseRepository.findByUserId(userId);
        if (courses.isEmpty()) {
            throw new ResourceNotFoundException("No courses found for user with id " + userId);
        }
        return courses;
    }

    public Course createCourse(Course course) {return courseRepository.save(course);}

    public Course updateCourse(Long id, Course courseDetails) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        course.setNombreCurso(courseDetails.getNombreCurso());
        course.setDescripcion(courseDetails.getDescripcion());
        course.setProfesor(courseDetails.getProfesor());
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        courseRepository.delete(course);
    }
}

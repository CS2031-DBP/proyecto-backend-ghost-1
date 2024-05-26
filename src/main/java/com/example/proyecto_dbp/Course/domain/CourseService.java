package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.Course.dto.CourseDTO;
import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    public List<CourseDTO> getAllCourses() {
        return courseRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public CourseDTO getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        return convertToDTO(course);
    }

    public List<CourseDTO> getCoursesByUserId(Long userId) {
        return courseRepository.findAll().stream()
                .filter(course -> course.getUser().getId().equals(userId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        course = courseRepository.save(course);
        return convertToDTO(course);
    }

    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id));
        course.setNombreCurso(courseDTO.getNombreCurso());
        course.setDescripcion(courseDTO.getDescripcion());
        course.setProfesor(courseDTO.getProfesor());
        course = courseRepository.save(course);
        return convertToDTO(course);
    }

    public void deleteCourse(Long id) {
        if (!courseRepository.existsById(id)) throw new ResourceNotFoundException("Course not found with id " + id);
        courseRepository.deleteById(id);
    }

    private CourseDTO convertToDTO(Course course) {
        return CourseDTO.builder()
                .Courseid(course.getCourseid())
                .nombreCurso(course.getNombreCurso())
                .descripcion(course.getDescripcion())
                .profesor(course.getProfesor())
                .userId(course.getUser().getId())
                .build();
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setNombreCurso(courseDTO.getNombreCurso());
        course.setDescripcion(courseDTO.getDescripcion());
        course.setProfesor(courseDTO.getProfesor());
        userRepository.findById(courseDTO.getUserId())
                .ifPresent(course::setUser);  // Asegúrate de que la entidad Course tiene un método setUser que acepte un User
        return course;
    }
}

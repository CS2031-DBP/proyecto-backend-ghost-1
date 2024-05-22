package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.Course.dto.CourseDTO;
import com.example.proyecto_dbp.Course.domain.Course;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public Optional<CourseDTO> getCourseById(Long id) {
        return Optional.ofNullable(courseRepository.findById(id)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found with id " + id)));
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
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found with id " + id);
        }
        courseRepository.deleteById(id);
    }

    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getId());
        courseDTO.setNombreCurso(course.getNombreCurso());
        courseDTO.setDescripcion(course.getDescripcion());
        courseDTO.setProfesor(course.getProfesor());
        courseDTO.setUserId(course.getUser().getId());
        return courseDTO;
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setNombreCurso(courseDTO.getNombreCurso());
        course.setDescripcion(courseDTO.getDescripcion());
        course.setProfesor(courseDTO.getProfesor());
        userRepository.findById(courseDTO.getUserId()).ifPresent(course::setUser);
        return course;
    }
}

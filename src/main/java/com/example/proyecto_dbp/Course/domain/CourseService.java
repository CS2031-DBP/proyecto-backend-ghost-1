package com.example.proyecto_dbp.Course.domain;

import com.example.proyecto_dbp.Course.dto.CourseDTO;
import com.example.proyecto_dbp.Course.domain.Course;

import com.example.proyecto_dbp.Course.infrastructure.CourseRepository;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public CourseDTO createCourse(CourseDTO courseDTO) {
        Course course = convertToEntity(courseDTO);
        course = courseRepository.save(course);
        return convertToDTO(course);
    }

    // Other service methods

    private CourseDTO convertToDTO(Course course) {
        CourseDTO courseDTO = new CourseDTO();
        courseDTO.setId(course.getCourse_id());
        courseDTO.setNombreCurso(course.getCourse_name());
        courseDTO.setDescripcion(course.getCourse_description());
        courseDTO.setProfesor(course.getProfessor());
        courseDTO.setUserId(course.getUser().getId());
        return courseDTO;
    }

    private Course convertToEntity(CourseDTO courseDTO) {
        Course course = new Course();
        course.setCourse_name(courseDTO.getNombreCurso());
        course.setCourse_description(courseDTO.getDescripcion());
        course.setProfessor(courseDTO.getProfesor());
        userRepository.findById(courseDTO.getUserId()).ifPresent(course::setUser);
        return course;
    }
}

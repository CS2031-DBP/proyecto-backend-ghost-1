package com.example.proyecto_dbp.Course.application;

import com.example.proyecto_dbp.Course.domain.Course;
import com.example.proyecto_dbp.Course.domain.CourseService;
import com.example.proyecto_dbp.Course.dto.CourseDTO;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> courses = courseService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if (course != null) return ResponseEntity.ok(course);
        else return ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Course>> getCoursesByUserId(@PathVariable Long userId) {
        List<Course> courses = courseService.getCoursesByUserId(userId);
        return ResponseEntity.ok(courses);
    }

    @PostMapping
    public ResponseEntity<Course> createCourse(@RequestBody @Valid Course course) {
        Course createdCourse = courseService.createCourse(course);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody @Valid Course course) {
        Course updatedCourse = courseService.updateCourse(id, course);
        if (updatedCourse != null) return ResponseEntity.ok(updatedCourse);
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.noContent().build();
        }
        catch (ResourceNotFoundException e) {return ResponseEntity.notFound().build();}
    }
}

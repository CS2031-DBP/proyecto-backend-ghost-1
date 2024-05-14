package com.example.proyecto_dbp.Course.application;

import com.example.proyecto_dbp.Course.dto.*;
import com.example.proyecto_dbp.Course.domain.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseInputDto courseInputDto) {
        CourseDto courseDto = courseService.createCourse(courseInputDto);
        CourseResponseDto responseDto = new CourseResponseDto(courseDto.getId(), courseDto.getName());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        CourseDto courseDto = courseService.findCourseById(id);
        CourseResponseDto responseDto = new CourseResponseDto(courseDto.getId(), courseDto.getName());
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long id, @RequestBody CourseInputDto courseInputDto) {
        CourseDto courseDto = courseService.updateCourse(id, courseInputDto);
        CourseResponseDto responseDto = new CourseResponseDto(courseDto.getId(), courseDto.getName());
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return ResponseEntity.noContent().build();
    }

}
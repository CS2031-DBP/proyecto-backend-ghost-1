package com.example.proyecto_dbp.Course.application;

import com.example.proyecto_dbp.Course.domain.Course;
import com.example.proyecto_dbp.Course.domain.CourseService;
import com.example.proyecto_dbp.GlobalExceptionHandler;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    private Course course;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(courseController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        course = new Course();
        course.setId(1L);
        course.setNombreCurso("Test Course");
        course.setDescripcion("Test Description");
        course.setProfesor("Test Professor");

        when(courseService.getCourseById(anyLong())).thenReturn(course);
        when(courseService.createCourse(any(Course.class))).thenReturn(course);
        when(courseService.updateCourse(anyLong(), any(Course.class))).thenReturn(course);
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courses = Collections.singletonList(course);
        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/courses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(course.getId()))
                .andExpect(jsonPath("$[0].nombreCurso").value(course.getNombreCurso()))
                .andExpect(jsonPath("$[0].descripcion").value(course.getDescripcion()));
    }

    @Test
    public void testGetCourseById() throws Exception {
        mockMvc.perform(get("/courses/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.nombreCurso").value(course.getNombreCurso()))
                .andExpect(jsonPath("$.descripcion").value(course.getDescripcion()));
    }

    @Test
    public void testGetCourseByIdNotFound() throws Exception {
        when(courseService.getCourseById(anyLong())).thenThrow(new ResourceNotFoundException("Course not found"));

        mockMvc.perform(get("/courses/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course not found"));
    }

    @Test
    public void testCreateCourse() throws Exception {
        mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreCurso\":\"New Course\",\"descripcion\":\"New Description\",\"profesor\":\"New Professor\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.nombreCurso").value(course.getNombreCurso()))
                .andExpect(jsonPath("$.descripcion").value(course.getDescripcion()));
    }

    @Test
    public void testUpdateCourse() throws Exception {
        mockMvc.perform(put("/courses/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreCurso\":\"Updated Course\",\"descripcion\":\"Updated Description\",\"profesor\":\"Updated Professor\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId()))
                .andExpect(jsonPath("$.nombreCurso").value(course.getNombreCurso()))
                .andExpect(jsonPath("$.descripcion").value(course.getDescripcion()));
    }

    @Test
    public void testUpdateCourseNotFound() throws Exception {
        when(courseService.updateCourse(anyLong(), any(Course.class))).thenThrow(new ResourceNotFoundException("Course not found"));

        mockMvc.perform(put("/courses/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombreCurso\":\"Updated Course\",\"descripcion\":\"Updated Description\",\"profesor\":\"Updated Professor\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course not found"));
    }

    @Test
    public void testDeleteCourse() throws Exception {
        mockMvc.perform(delete("/courses/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCourseNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Course not found")).when(courseService).deleteCourse(anyLong());

        mockMvc.perform(delete("/courses/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Course not found"));
    }
}

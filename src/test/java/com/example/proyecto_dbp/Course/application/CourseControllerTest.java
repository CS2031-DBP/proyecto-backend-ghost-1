package com.example.proyecto_dbp.Course.application;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.proyecto_dbp.Course.domain.Course;
import com.example.proyecto_dbp.Course.domain.CourseService;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CourseService courseService;

    @InjectMocks
    private CourseController courseController;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(courseController).build();
    }

    @Test
    public void testGetAllCourses() throws Exception {
        List<Course> courses = Arrays.asList(new Course(), new Course());
        when(courseService.getAllCourses()).thenReturn(courses);

        mockMvc.perform(get("/courses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(courses.size()));
    }

    @Test
    public void testGetCourseById() throws Exception {
        Course course = new Course();
        when(courseService.getCourseById(1L)).thenReturn(course);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(course.getId()));
    }

    @Test
    public void testGetCourseById_NotFound() throws Exception {
        when(courseService.getCourseById(1L)).thenReturn(null);

        mockMvc.perform(get("/courses/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCourse() throws Exception {
        Course course = new Course();
        course.setNombreCurso("New Course");
        course.setDescripcion("Description of the new course");
        course.setProfesor("Professor Name");

        User user = new User();
        user.setId(1L);
        course.setUser(user);

        when(courseService.createCourse(any(Course.class))).thenReturn(course);

        String courseJson = "{\"nombreCurso\":\"New Course\", \"descripcion\":\"Description of the new course\", \"profesor\":\"Professor Name\", \"user\":{\"id\":1}}";

        MvcResult result = mockMvc.perform(post("/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson))
                .andReturn();

        MockHttpServletResponse response = result.getResponse();
        String jsonResponse = response.getContentAsString();

        System.out.println("Response JSON: " + jsonResponse);

        assert response.getStatus() == 201 : "Expected status 201 but was " + response.getStatus();
        assert jsonResponse.contains("New Course") : "Response does not contain 'New Course'";
        assert jsonResponse.contains("Description of the new course") : "Response does not contain 'Description of the new course'";
        assert jsonResponse.contains("Professor Name") : "Response does not contain 'Professor Name'";
        assert jsonResponse.contains("\"id\":1") : "Response does not contain expected user id data";
    }

    @Test
    public void testUpdateCourse() throws Exception {
        Course existingCourse = new Course();
        existingCourse.setId(1L);
        when(courseService.updateCourse(eq(1L), any(Course.class))).thenReturn(existingCourse);

        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testUpdateCourse_NotFound() throws Exception {
        when(courseService.updateCourse(eq(1L), any(Course.class))).thenReturn(null);

        mockMvc.perform(put("/courses/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Updated Name\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCourse() throws Exception {
        doNothing().when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/courses/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCourse_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Course not found")).when(courseService).deleteCourse(1L);

        mockMvc.perform(delete("/courses/1"))
                .andExpect(status().isNotFound());
    }
}
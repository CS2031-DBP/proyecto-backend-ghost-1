package com.example.proyecto_dbp.Task.infrastructure;

import com.example.proyecto_dbp.GlobalExceptionHandler;
import com.example.proyecto_dbp.Task.application.TaskController;
import com.example.proyecto_dbp.Task.domain.Task;
import com.example.proyecto_dbp.Task.domain.TaskService;
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
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskRepositoryTest {

    private MockMvc mockMvc;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private Task task;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(taskController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        task = new Task();
        task.setId(1L);
        task.setTitulo("Test Task");
        task.setDescripcion("Test Description");
        task.setPriority("High");
        task.setCompleted(false);

        when(taskService.getTaskById(anyLong())).thenReturn(task);
        when(taskService.createTask(any(Task.class))).thenReturn(task);
        when(taskService.updateTask(anyLong(), any(Task.class))).thenReturn(task);
    }

    @Test
    public void testGetAllTasks() throws Exception {
        List<Task> tasks = Collections.singletonList(task);
        when(taskService.getAllTasks()).thenReturn(tasks);

        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(task.getId()))
                .andExpect(jsonPath("$[0].titulo").value(task.getTitulo()))
                .andExpect(jsonPath("$[0].descripcion").value(task.getDescripcion()));
    }

    @Test
    public void testGetTaskById() throws Exception {
        mockMvc.perform(get("/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.titulo").value(task.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(task.getDescripcion()));
    }

    @Test
    public void testCreateTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"New Task\",\"descripcion\":\"New Description\",\"priority\":\"High\",\"completed\":false,\"courseId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.titulo").value(task.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(task.getDescripcion()));
    }

    @Test
    public void testUpdateTask() throws Exception {
        mockMvc.perform(put("/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Task\",\"descripcion\":\"Updated Description\",\"priority\":\"Low\",\"completed\":true,\"courseId\":1}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(task.getId()))
                .andExpect(jsonPath("$.titulo").value(task.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(task.getDescripcion()));
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }

}

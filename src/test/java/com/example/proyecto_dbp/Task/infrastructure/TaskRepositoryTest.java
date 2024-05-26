package com.example.proyecto_dbp.Task.infrastructure;

import com.example.proyecto_dbp.Task.application.TaskController;
import com.example.proyecto_dbp.Task.domain.TaskService;
import com.example.proyecto_dbp.Task.dto.TaskDTO;
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

    private TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();

        // Crear TaskDTO
        taskDTO = new TaskDTO();
        taskDTO.setId(1L);
        taskDTO.setTitulo("Test Task");
        taskDTO.setDescripcion("Test Description");
        taskDTO.setPriority("High");
        taskDTO.setCompleted(false);
        taskDTO.setCourseId(1L);

        when(taskService.getTaskById(anyLong())).thenReturn(taskDTO);
        when(taskService.createTask(any(TaskDTO.class))).thenReturn(taskDTO);
        when(taskService.updateTask(anyLong(), any(TaskDTO.class))).thenReturn(taskDTO);
    }

    @Test
    public void testGetAllTasks() throws Exception {
        mockMvc.perform(get("/tasks")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTaskById() throws Exception {
        mockMvc.perform(get("/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTaskByIdNotFound() throws Exception {
        when(taskService.getTaskById(anyLong())).thenThrow(new ResourceNotFoundException("Task not found"));
        mockMvc.perform(get("/tasks/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateTask() throws Exception {
        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"New Task\",\"descripcion\":\"New Description\",\"priority\":\"High\",\"completed\":false,\"courseId\":1}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testUpdateTask() throws Exception {
        mockMvc.perform(put("/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Task\",\"descripcion\":\"Updated Description\",\"priority\":\"Low\",\"completed\":true,\"courseId\":1}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateTaskNotFound() throws Exception {
        when(taskService.updateTask(anyLong(), any(TaskDTO.class))).thenThrow(new ResourceNotFoundException("Task not found"));
        mockMvc.perform(put("/tasks/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Task\",\"descripcion\":\"Updated Description\",\"priority\":\"Low\",\"completed\":true,\"courseId\":1}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteTask() throws Exception {
        mockMvc.perform(delete("/tasks/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTaskNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Task not found")).when(taskService).deleteTask(anyLong());
        mockMvc.perform(delete("/tasks/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}

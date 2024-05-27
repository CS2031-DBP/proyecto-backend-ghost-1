package com.example.proyecto_dbp.Activity.infrastructure;

import com.example.proyecto_dbp.Activity.application.ActivityController;
import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.Activity.domain.ActivityService;
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
public class ActivityRepositoryTest {

    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    private Activity activity;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(activityController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        activity = new Activity();
        activity.setId(1L);
        activity.setTitulo("Test Activity");
        activity.setDescripcion("Test Description");

        when(activityService.getActivityById(anyLong())).thenReturn(activity);
        when(activityService.createActivity(any(Activity.class))).thenReturn(activity);
        when(activityService.updateActivity(anyLong(), any(Activity.class))).thenReturn(activity);
    }

    @Test
    public void testGetAllActivities() throws Exception {
        List<Activity> activities = Collections.singletonList(activity);
        when(activityService.getAllActivities()).thenReturn(activities);

        mockMvc.perform(get("/activities")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(activity.getId()))
                .andExpect(jsonPath("$[0].titulo").value(activity.getTitulo()))
                .andExpect(jsonPath("$[0].descripcion").value(activity.getDescripcion()));
    }

    @Test
    public void testGetActivityById() throws Exception {
        mockMvc.perform(get("/activities/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(activity.getId()))
                .andExpect(jsonPath("$.titulo").value(activity.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(activity.getDescripcion()));
    }

    @Test
    public void testCreateActivity() throws Exception {
        mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"New Activity\",\"descripcion\":\"New Description\",\"allDay\":false,\"location\":\"New Location\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(activity.getId()))
                .andExpect(jsonPath("$.titulo").value(activity.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(activity.getDescripcion()));
    }

    @Test
    public void testUpdateActivity() throws Exception {
        mockMvc.perform(put("/activities/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Activity\",\"descripcion\":\"Updated Description\",\"allDay\":true,\"location\":\"Updated Location\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(activity.getId()))
                .andExpect(jsonPath("$.titulo").value(activity.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(activity.getDescripcion()));
    }

    @Test
    public void testDeleteActivity() throws Exception {
        mockMvc.perform(delete("/activities/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetActivityByIdNotFound() throws Exception {
        when(activityService.getActivityById(anyLong())).thenThrow(new ResourceNotFoundException("Activity not found with id 999"));

        mockMvc.perform(get("/activities/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Activity not found with id 999"));
    }

    @Test
    public void testDeleteActivityNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Activity not found with id 999")).when(activityService).deleteActivity(anyLong());

        mockMvc.perform(delete("/activities/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Activity not found with id 999"));
    }

    @Test
    public void testUpdateActivityNotFound() throws Exception {
        when(activityService.updateActivity(anyLong(), any(Activity.class))).thenThrow(new ResourceNotFoundException("Activity not found with id 999"));

        mockMvc.perform(put("/activities/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Activity\",\"descripcion\":\"Updated Description\",\"allDay\":true,\"location\":\"Updated Location\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Activity not found with id 999"));
    }
}

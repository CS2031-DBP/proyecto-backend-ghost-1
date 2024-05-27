package com.example.proyecto_dbp.Activity.application;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.Activity.domain.ActivityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ActivityControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ActivityService activityService;

    @InjectMocks
    private ActivityController activityController;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(activityController).build();
    }

    @Test
    public void testGetAllActivities() throws Exception {
        List<Activity> activities = Arrays.asList(new Activity(), new Activity());
        when(activityService.getAllActivities()).thenReturn(activities);

        mockMvc.perform(get("/activities"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(activities.size()));
    }

    @Test
    public void testGetActivityById() throws Exception {
        Activity activity = new Activity();
        activity.setId(1L);
        when(activityService.getActivityById(1L)).thenReturn(activity);

        mockMvc.perform(get("/activities/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    public void testGetActivitiesByCourseId() throws Exception {
        List<Activity> activities = Arrays.asList(new Activity(), new Activity());
        when(activityService.getActivitiesByCourseId(1L)).thenReturn(activities);

        mockMvc.perform(get("/activities/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(activities.size()));
    }

    @Test
    public void testCreateActivity() throws Exception {
        Activity activity = new Activity();
        activity.setTitulo("New Activity");
        when(activityService.createActivity(any(Activity.class))).thenReturn(activity);

        mockMvc.perform(post("/activities")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"New Activity\"}"))  // Ajuste aquí para que coincida con el campo correcto
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("New Activity"));  // Verificación ajustada para el campo 'titulo'
    }

    @Test
    public void testUpdateActivity() throws Exception {
        Activity activity = new Activity();
        activity.setId(1L);
        activity.setTitulo("Updated Activity");
        when(activityService.updateActivity(eq(1L), any(Activity.class))).thenReturn(activity);

        mockMvc.perform(put("/activities/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Activity\"}"))  // Ajuste aquí para que coincida con el campo correcto
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Updated Activity"));  // Verificación ajustada para el campo 'titulo'
    }

    @Test
    public void testDeleteActivity() throws Exception {
        doNothing().when(activityService).deleteActivity(1L);

        mockMvc.perform(delete("/activities/1"))
                .andExpect(status().isNoContent());
    }
}

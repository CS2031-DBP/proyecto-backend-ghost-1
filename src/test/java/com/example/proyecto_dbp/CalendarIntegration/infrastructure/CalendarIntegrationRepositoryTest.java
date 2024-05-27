package com.example.proyecto_dbp.CalendarIntegration.infrastructure;

import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegration;
import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegrationService;
import com.example.proyecto_dbp.CalendarIntegration.application.CalendarIntegrationController;
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
public class CalendarIntegrationRepositoryTest {

    private MockMvc mockMvc;

    @Mock
    private CalendarIntegrationService calendarIntegrationService;

    @InjectMocks
    private CalendarIntegrationController calendarIntegrationController;

    private CalendarIntegration calendarIntegration;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(calendarIntegrationController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        calendarIntegration = new CalendarIntegration();
        calendarIntegration.setActivityId(1L);
        calendarIntegration.setGoogleCalendarEventId("test-calendar-id");

        when(calendarIntegrationService.getCalendarIntegrationByActivityId(anyLong())).thenReturn(calendarIntegration);
        when(calendarIntegrationService.createCalendarIntegration(any(CalendarIntegration.class))).thenReturn(calendarIntegration);
        when(calendarIntegrationService.updateCalendarIntegration(anyLong(), any(CalendarIntegration.class))).thenReturn(calendarIntegration);
    }

    @Test
    public void testGetAllCalendarIntegrations() throws Exception {
        List<CalendarIntegration> calendarIntegrations = Collections.singletonList(calendarIntegration);
        when(calendarIntegrationService.getAllCalendarIntegrations()).thenReturn(calendarIntegrations);

        mockMvc.perform(get("/calendarIntegrations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(calendarIntegration.getActivityId()))
                .andExpect(jsonPath("$[0].calendarId").value(calendarIntegration.getGoogleCalendarEventId()));
    }

    @Test
    public void testGetCalendarIntegrationById() throws Exception {
        mockMvc.perform(get("/calendarIntegrations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(calendarIntegration.getActivityId()))
                .andExpect(jsonPath("$.calendarId").value(calendarIntegration.getGoogleCalendarEventId()));
    }

    @Test
    public void testCreateCalendarIntegration() throws Exception {
        mockMvc.perform(post("/calendarIntegrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"calendarId\":\"new-calendar-id\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(calendarIntegration.getActivityId()))
                .andExpect(jsonPath("$.calendarId").value(calendarIntegration.getGoogleCalendarEventId()));
    }

    @Test
    public void testUpdateCalendarIntegration() throws Exception {
        mockMvc.perform(put("/calendarIntegrations/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"calendarId\":\"updated-calendar-id\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(calendarIntegration.getActivityId()))
                .andExpect(jsonPath("$.calendarId").value(calendarIntegration.getGoogleCalendarEventId()));
    }

    @Test
    public void testDeleteCalendarIntegration() throws Exception {
        mockMvc.perform(delete("/calendarIntegrations/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetCalendarIntegrationByIdNotFound() throws Exception {
        when(calendarIntegrationService.getCalendarIntegrationByActivityId(anyLong())).thenThrow(new ResourceNotFoundException("CalendarIntegration not found with id 999"));

        mockMvc.perform(get("/calendarIntegrations/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("CalendarIntegration not found with id 999"));
    }

    @Test
    public void testDeleteCalendarIntegrationNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("CalendarIntegration not found with id 999")).when(calendarIntegrationService).deleteCalendarIntegration(anyLong());

        mockMvc.perform(delete("/calendarIntegrations/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("CalendarIntegration not found with id 999"));
    }

    @Test
    public void testUpdateCalendarIntegrationNotFound() throws Exception {
        when(calendarIntegrationService.updateCalendarIntegration(anyLong(), any(CalendarIntegration.class))).thenThrow(new ResourceNotFoundException("CalendarIntegration not found with id 999"));

        mockMvc.perform(put("/calendarIntegrations/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userId\":1,\"calendarId\":\"updated-calendar-id\"}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("CalendarIntegration not found with id 999"));
    }
}

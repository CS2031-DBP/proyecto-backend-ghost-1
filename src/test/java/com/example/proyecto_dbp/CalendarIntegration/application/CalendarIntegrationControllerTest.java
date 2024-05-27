package com.example.proyecto_dbp.CalendarIntegration.application;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;

import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegration;
import com.example.proyecto_dbp.CalendarIntegration.domain.CalendarIntegrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class CalendarIntegrationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private CalendarIntegrationService calendarIntegrationService;

    @InjectMocks
    private CalendarIntegrationController calendarIntegrationController;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(calendarIntegrationController).build();
    }

    @Test
    public void testGetAllCalendarIntegrations() throws Exception {
        List<CalendarIntegration> integrations = Arrays.asList(new CalendarIntegration(), new CalendarIntegration());
        when(calendarIntegrationService.getAllCalendarIntegrations()).thenReturn(integrations);

        mockMvc.perform(get("/calendar-integrations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(integrations.size()));
    }

    @Test
    public void testGetCalendarIntegrationByActivityId() throws Exception {
        CalendarIntegration integration = new CalendarIntegration();
        integration.setActivityId(1L);
        when(calendarIntegrationService.getCalendarIntegrationByActivityId(1L)).thenReturn(integration);

        mockMvc.perform(get("/calendar-integrations/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.activityId").value(1));
    }

    @Test
    public void testGetCalendarIntegrationByActivityId_NotFound() throws Exception {
        when(calendarIntegrationService.getCalendarIntegrationByActivityId(1L)).thenReturn(null);

        mockMvc.perform(get("/calendar-integrations/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateCalendarIntegration() throws Exception {
        CalendarIntegration integration = new CalendarIntegration();
        integration.setGoogleCalendarEventId("New Integration");
        when(calendarIntegrationService.createCalendarIntegration(any(CalendarIntegration.class))).thenReturn(integration);

        mockMvc.perform(post("/calendar-integrations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"googleCalendarEventId\":\"New Integration\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.googleCalendarEventId").value("New Integration"));
    }


    @Test
    public void testUpdateCalendarIntegration() throws Exception {
        CalendarIntegration integration = new CalendarIntegration();
        integration.setActivityId(1L);
        integration.setGoogleCalendarEventId("Updated Integration");

        when(calendarIntegrationService.updateCalendarIntegration(eq(1L), any(CalendarIntegration.class))).thenReturn(integration);

        mockMvc.perform(put("/calendar-integrations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"googleCalendarEventId\":\"Updated Integration\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.googleCalendarEventId").value("Updated Integration"));
    }


    @Test
    public void testUpdateCalendarIntegration_NotFound() throws Exception {
        when(calendarIntegrationService.updateCalendarIntegration(eq(1L), any(CalendarIntegration.class))).thenReturn(null);

        mockMvc.perform(put("/calendar-integrations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Updated Integration\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeleteCalendarIntegration() throws Exception {
        doNothing().when(calendarIntegrationService).deleteCalendarIntegration(1L);

        mockMvc.perform(delete("/calendar-integrations/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteCalendarIntegration_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Integration not found")).when(calendarIntegrationService).deleteCalendarIntegration(1L);

        mockMvc.perform(delete("/calendar-integrations/1"))
                .andExpect(status().isNotFound());
    }
}
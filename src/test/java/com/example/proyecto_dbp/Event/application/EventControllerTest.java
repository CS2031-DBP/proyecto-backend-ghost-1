package com.example.proyecto_dbp.Event.application;

import  static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.example.proyecto_dbp.Event.domain.Event;
import com.example.proyecto_dbp.Event.domain.EventService;
import com.example.proyecto_dbp.Event.events.EmailService;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @Mock
    private EmailService emailService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    public void testSendTestEmail() throws Exception {
        doNothing().when(emailService).sendHtmlMessage(anyString(), anyString(), anyString());

        mockMvc.perform(post("/events/send-test-email")
                        .param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Correo enviado exitosamente"));
    }

    @Test
    public void testGetAllEvents() throws Exception {
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(events.size()));
    }

    @Test
    public void testGetEventById() throws Exception {
        Event event = new Event();
        event.setId(1L);
        when(eventService.getEventById(1L)).thenReturn(event);

        mockMvc.perform(get("/events/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    public void testGetEventsByCourseId() throws Exception {
        List<Event> events = Arrays.asList(new Event(), new Event());
        when(eventService.getEventsByCourseId(1L)).thenReturn(events);

        mockMvc.perform(get("/events/course/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(events.size()));
    }

    @Test
    public void testCreateEvent() throws Exception {
        Event event = new Event();
        event.setTitulo("New Event");
        when(eventService.createEvent(any(Event.class))).thenReturn(event);

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"New Event\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("New Event"));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        Event event = new Event();
        event.setId(1L);
        event.setTitulo("Updated Event");
        when(eventService.updateEvent(eq(1L), any(Event.class))).thenReturn(event);

        mockMvc.perform(put("/events/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Event\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Updated Event"));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        doNothing().when(eventService).deleteEvent(1L);

        mockMvc.perform(delete("/events/1"))
                .andExpect(status().isNoContent());
    }

}

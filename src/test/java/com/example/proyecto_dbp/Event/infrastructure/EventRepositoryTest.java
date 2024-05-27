package com.example.proyecto_dbp.Event.infrastructure;

import com.example.proyecto_dbp.GlobalExceptionHandler;
import com.example.proyecto_dbp.Event.application.EventController;
import com.example.proyecto_dbp.Event.domain.Event;
import com.example.proyecto_dbp.Event.domain.EventService;
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
public class EventRepositoryTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(eventController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        event = new Event();
        event.setId(1L);
        event.setTitulo("Test Event");
        event.setDescripcion("Test Description");

        when(eventService.getEventById(anyLong())).thenReturn(event);
        when(eventService.createEvent(any(Event.class))).thenReturn(event);
        when(eventService.updateEvent(anyLong(), any(Event.class))).thenReturn(event);
    }

    @Test
    public void testGetAllEvents() throws Exception {
        List<Event> events = Collections.singletonList(event);
        when(eventService.getAllEvents()).thenReturn(events);

        mockMvc.perform(get("/events")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(event.getId()))
                .andExpect(jsonPath("$[0].titulo").value(event.getTitulo()))
                .andExpect(jsonPath("$[0].descripcion").value(event.getDescripcion()));
    }

    @Test
    public void testGetEventById() throws Exception {
        mockMvc.perform(get("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.titulo").value(event.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(event.getDescripcion()));
    }

    @Test
    public void testCreateEvent() throws Exception {
        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"New Event\",\"descripcion\":\"New Description\",\"priority\":\"High\",\"completed\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.titulo").value(event.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(event.getDescripcion()));
    }

    @Test
    public void testUpdateEvent() throws Exception {
        mockMvc.perform(put("/events/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Event\",\"descripcion\":\"Updated Description\",\"priority\":\"Low\",\"completed\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(event.getId()))
                .andExpect(jsonPath("$.titulo").value(event.getTitulo()))
                .andExpect(jsonPath("$.descripcion").value(event.getDescripcion()));
    }

    @Test
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(delete("/events/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetEventByIdNotFound() throws Exception {
        when(eventService.getEventById(anyLong())).thenThrow(new ResourceNotFoundException("Event not found with id 999"));

        mockMvc.perform(get("/events/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Event not found with id 999"));
    }

    @Test
    public void testDeleteEventNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("Event not found with id 999")).when(eventService).deleteEvent(anyLong());

        mockMvc.perform(delete("/events/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Event not found with id 999"));
    }

    @Test
    public void testUpdateEventNotFound() throws Exception {
        when(eventService.updateEvent(anyLong(), any(Event.class))).thenThrow(new ResourceNotFoundException("Event not found with id 999"));

        mockMvc.perform(put("/events/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"titulo\":\"Updated Event\",\"descripcion\":\"Updated Description\",\"priority\":\"Low\",\"completed\":true}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Event not found with id 999"));
    }
}

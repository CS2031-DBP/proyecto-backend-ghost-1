package com.example.proyecto_dbp.VoiceCommand.infrastructure;

import com.example.proyecto_dbp.GlobalExceptionHandler;
import com.example.proyecto_dbp.VoiceCommand.application.VoiceCommandController;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
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

import java.util.Optional;
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
public class VoiceCommandRepositoryTest {

    private MockMvc mockMvc;

    @Mock
    private VoiceCommandService voiceCommandService;

    @InjectMocks
    private VoiceCommandController voiceCommandController;

    private VoiceCommand voiceCommand;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(voiceCommandController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        voiceCommand = new VoiceCommand();
        voiceCommand.setId(1L);
        voiceCommand.setCommand("Test Command");

        when(voiceCommandService.getVoiceCommandById(anyLong())).thenReturn(Optional.of(voiceCommand));
        when(voiceCommandService.createVoiceCommand(any(VoiceCommand.class))).thenReturn(voiceCommand);
        when(voiceCommandService.updateVoiceCommand(anyLong(), any(VoiceCommand.class))).thenReturn(Optional.of(voiceCommand));
    }

    @Test
    public void testGetAllVoiceCommands() throws Exception {
        List<VoiceCommand> voiceCommands = Collections.singletonList(voiceCommand);
        when(voiceCommandService.getAllVoiceCommands()).thenReturn(voiceCommands);

        mockMvc.perform(get("/voiceCommands")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(voiceCommand.getId()))
                .andExpect(jsonPath("$[0].command").value(voiceCommand.getCommand()));
    }

    @Test
    public void testGetVoiceCommandById() throws Exception {
        mockMvc.perform(get("/voiceCommands/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(voiceCommand.getId()))
                .andExpect(jsonPath("$.command").value(voiceCommand.getCommand()));
    }

    @Test
    public void testCreateVoiceCommand() throws Exception {
        mockMvc.perform(post("/voiceCommands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"New Command\",\"response\":\"New Response\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(voiceCommand.getId()))
                .andExpect(jsonPath("$.command").value(voiceCommand.getCommand()));
    }

    @Test
    public void testUpdateVoiceCommand() throws Exception {
        mockMvc.perform(put("/voiceCommands/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"Updated Command\",\"response\":\"Updated Response\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(voiceCommand.getId()))
                .andExpect(jsonPath("$.command").value(voiceCommand.getCommand()));
    }

    @Test
    public void testDeleteVoiceCommand() throws Exception {
        mockMvc.perform(delete("/voiceCommands/{id}", 1L))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetVoiceCommandByIdNotFound() throws Exception {
        when(voiceCommandService.getVoiceCommandById(anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("/voiceCommands/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("VoiceCommand not found with id 999"));
    }

    @Test
    public void testDeleteVoiceCommandNotFound() throws Exception {
        doThrow(new ResourceNotFoundException("VoiceCommand not found with id 999")).when(voiceCommandService).deleteVoiceCommand(anyLong());

        mockMvc.perform(delete("/voiceCommands/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("VoiceCommand not found with id 999"));
    }

    @Test
    public void testUpdateVoiceCommandNotFound() throws Exception {
        when(voiceCommandService.updateVoiceCommand(anyLong(), any(VoiceCommand.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/voiceCommands/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"Updated Command\",\"response\":\"Updated Response\"}"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("VoiceCommand not found with id 999"));
    }
}

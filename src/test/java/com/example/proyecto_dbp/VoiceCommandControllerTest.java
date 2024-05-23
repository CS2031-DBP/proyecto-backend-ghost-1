package com.example.proyecto_dbp;

import com.example.proyecto_dbp.VoiceCommand.application.VoiceCommandController;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDTO;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VoiceCommandController.class)
public class VoiceCommandControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VoiceCommandService voiceCommandService;

    @InjectMocks
    private VoiceCommandController voiceCommandController;

    @Test
    public void getAllVoiceCommands() throws Exception {
        VoiceCommandDTO voiceCommand1 = new VoiceCommandDTO(1L, "Command1", "Description1", 1L, 1L);
        VoiceCommandDTO voiceCommand2 = new VoiceCommandDTO(2L, "Command2", "Description2", 1L, 2L);

        when(voiceCommandService.getAllVoiceCommands()).thenReturn(Arrays.asList(voiceCommand1, voiceCommand2));

        mockMvc = MockMvcBuilders.standaloneSetup(voiceCommandController).build();
        mockMvc.perform(get("/voicecommands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].comando").value("Command1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].comando").value("Command2"));
    }

    @Test
    public void getVoiceCommandById() throws Exception {
        VoiceCommandDTO voiceCommand = new VoiceCommandDTO(1L, "Command1", "Description1", 1L, 1L);

        when(voiceCommandService.getVoiceCommandById(1L)).thenReturn(Optional.of(voiceCommand));

        mockMvc = MockMvcBuilders.standaloneSetup(voiceCommandController).build();
        mockMvc.perform(get("/voicecommands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.comando").value("Command1"));
    }

    @Test
    public void createVoiceCommand() throws Exception {
        VoiceCommandDTO voiceCommand = new VoiceCommandDTO(1L, "Command1", "Description1", 1L, 1L);

        when(voiceCommandService.createVoiceCommand(voiceCommand)).thenReturn(voiceCommand);

        mockMvc = MockMvcBuilders.standaloneSetup(voiceCommandController).build();
        mockMvc.perform(post("/voicecommands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"comando\": \"Command1\", \"descripcionAccion\": \"Description1\", \"userId\": 1, \"activityId\": 1 }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.comando").value("Command1"));
    }
}

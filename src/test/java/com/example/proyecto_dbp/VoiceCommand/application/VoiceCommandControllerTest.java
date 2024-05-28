package com.example.proyecto_dbp.VoiceCommand.application;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
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
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class VoiceCommandControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VoiceCommandService voiceCommandService;

    @InjectMocks
    private VoiceCommandController voiceCommandController;

    @BeforeEach
    public void setup() {
        mockMvc = standaloneSetup(voiceCommandController).build();
    }

    @Test
    public void testGetAllVoiceCommands() throws Exception {
        List<VoiceCommand> voiceCommands = Arrays.asList(new VoiceCommand(), new VoiceCommand());
        when(voiceCommandService.getAllVoiceCommands()).thenReturn(voiceCommands);

        mockMvc.perform(get("/voicecommands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(voiceCommands.size()));
    }

    @Test
    public void testGetVoiceCommandById() throws Exception {
        VoiceCommand voiceCommand = new VoiceCommand();
        voiceCommand.setId(1L);
        when(voiceCommandService.getVoiceCommandById(1L)).thenReturn(Optional.of(voiceCommand));

        mockMvc.perform(get("/voicecommands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }


    @Test
    public void testGetVoiceCommandsByUserId() throws Exception {
        List<VoiceCommand> voiceCommands = Arrays.asList(new VoiceCommand(), new VoiceCommand());
        when(voiceCommandService.getVoiceCommandsByUserId(1L)).thenReturn(voiceCommands);

        mockMvc.perform(get("/voicecommands/user/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(voiceCommands.size()));
    }

    @Test
    public void testCreateVoiceCommand() throws Exception {
        VoiceCommand voiceCommand = new VoiceCommand();
        voiceCommand.setCommand("New Command");
        when(voiceCommandService.createVoiceCommand(any(VoiceCommand.class))).thenReturn(voiceCommand);

        mockMvc.perform(post("/voicecommands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"New Command\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.command").value("New Command"));
    }

    @Test
    public void testUpdateVoiceCommand() throws Exception {
        VoiceCommand voiceCommand = new VoiceCommand();
        voiceCommand.setId(1L);
        voiceCommand.setCommand("Updated Command");
        when(voiceCommandService.updateVoiceCommand(eq(1L), any(VoiceCommand.class))).thenReturn(Optional.of(voiceCommand));

        mockMvc.perform(put("/voicecommands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"command\":\"Updated Command\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.command").value("Updated Command"));
    }

    @Test
    public void testDeleteVoiceCommand() throws Exception {
        doNothing().when(voiceCommandService).deleteVoiceCommand(1L);

        mockMvc.perform(delete("/voicecommands/1"))
                .andExpect(status().isNoContent());
    }
}
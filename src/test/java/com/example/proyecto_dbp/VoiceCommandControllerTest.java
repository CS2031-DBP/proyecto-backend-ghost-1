package com.example.proyecto_dbp;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.VoiceCommand.application.VoiceCommandController;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoiceCommandController.class)
public class VoiceCommandControllerTest {

    private MockMvc mockMvc;

    @Mock
    private VoiceCommandService voiceCommandService;

    @InjectMocks
    private VoiceCommandController voiceCommandController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(voiceCommandController).build();
    }

    @Test
    public void getAllVoiceCommands() throws Exception {
        User user = new User();
        user.setId(1L);

        Activity activity1 = new Activity();
        activity1.setId(1L);

        Activity activity2 = new Activity();
        activity2.setId(2L);

        VoiceCommandDTO voiceCommand1 = new VoiceCommandDTO(1L, "Command1", "Description1", LocalDateTime.now(), user, activity1);
        VoiceCommandDTO voiceCommand2 = new VoiceCommandDTO(2L, "Command2", "Description2", LocalDateTime.now(), user, activity2);

        when(voiceCommandService.getAllVoiceCommands()).thenReturn(Arrays.asList(voiceCommand1, voiceCommand2));

        mockMvc.perform(get("/voicecommands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].command").value("Command1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].command").value("Command2"));
    }

    @Test
    public void getVoiceCommandById() throws Exception {
        User user = new User();
        user.setId(1L);

        Activity activity = new Activity();
        activity.setId(1L);

        VoiceCommandDTO voiceCommand = new VoiceCommandDTO(1L, "Command1", "Description1", LocalDateTime.now(), user, activity);

        when(voiceCommandService.getVoiceCommandById(1L)).thenReturn(Optional.of(voiceCommand));

        mockMvc.perform(get("/voicecommands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.command").value("Command1"));
    }

    @Test
    public void createVoiceCommand() throws Exception {
        User user = new User();
        user.setId(1L);

        Activity activity = new Activity();
        activity.setId(1L);

        VoiceCommandDTO voiceCommand = new VoiceCommandDTO(1L, "Command1", "Description1", LocalDateTime.now(), user, activity);

        when(voiceCommandService.createVoiceCommand(voiceCommand)).thenReturn(voiceCommand);

        mockMvc.perform(post("/voicecommands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"id\": 1, \"command\": \"Command1\", \"descriptionAction\": \"Description1\", \"timestamp\": \"2023-05-25T10:00:00\", \"user\": { \"id\": 1 }, \"activity\": { \"id\": 1 } }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.command").value("Command1"));
    }
}

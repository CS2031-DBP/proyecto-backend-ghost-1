package com.example.proyecto_dbp.VoiceCommand.application;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import java.util.List;

@RestController
@RequestMapping("/api/voiceCommands")
public class VoiceCommandController {
    @Autowired
    private VoiceCommandService voiceCommandService;

    @GetMapping("/user/{userId}")
    public List<VoiceCommand> getCommandsByUserId(@PathVariable Long userId) {
        return voiceCommandService.getCommandsByUser(userId);
    }

    // Más endpoints
}

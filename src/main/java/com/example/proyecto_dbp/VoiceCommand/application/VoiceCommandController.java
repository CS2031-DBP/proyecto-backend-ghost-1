package com.example.proyecto_dbp.VoiceCommand.application;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/voicecommands")
public class VoiceCommandController {

    @Autowired
    private VoiceCommandService voiceCommandService;

    @GetMapping
    public List<VoiceCommandDTO> getAllVoiceCommands() {
        return voiceCommandService.getAllVoiceCommands();
    }

    @PostMapping
    public ResponseEntity<VoiceCommandDTO> createVoiceCommand(@RequestBody VoiceCommandDTO voiceCommandDTO) {
        VoiceCommandDTO createdVoiceCommand = voiceCommandService.createVoiceCommand(voiceCommandDTO);
        return new ResponseEntity<>(createdVoiceCommand, HttpStatus.CREATED);
    }

    // Other CRUD operations
}

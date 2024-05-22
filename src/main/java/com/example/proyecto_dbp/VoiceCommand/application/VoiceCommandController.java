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

    // Get all voice commands
    @GetMapping
    public List<VoiceCommandDTO> getAllVoiceCommands() {return voiceCommandService.getAllVoiceCommands();}

    // Get voice command by ID
    @GetMapping("/{id}")
    public ResponseEntity<VoiceCommandDTO> getVoiceCommandById(@PathVariable Long id) {
        return ResponseEntity.of(voiceCommandService.getVoiceCommandById(id));
    }

    // Get voice commands by user ID
    @GetMapping("/user/{userId}")
    public List<VoiceCommandDTO> getVoiceCommandsByUserId(@PathVariable Long userId) {
        return voiceCommandService.getVoiceCommandsByUserId(userId);
    }

    // Create a new voice command
    @PostMapping
    public ResponseEntity<VoiceCommandDTO> createVoiceCommand(@RequestBody VoiceCommandDTO voiceCommandDTO) {
        VoiceCommandDTO createdVoiceCommand = voiceCommandService.createVoiceCommand(voiceCommandDTO);
        return new ResponseEntity<>(createdVoiceCommand, HttpStatus.CREATED);
    }

    // Update an existing voice command
    @PutMapping("/{id}")
    public ResponseEntity<VoiceCommandDTO> updateVoiceCommand(@PathVariable Long id, @RequestBody VoiceCommandDTO voiceCommandDTO) {
        VoiceCommandDTO updatedVoiceCommand = voiceCommandService.updateVoiceCommand(id, voiceCommandDTO);
        return ResponseEntity.ok(updatedVoiceCommand);
    }

    // Delete a voice command
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiceCommand(@PathVariable Long id) {
        voiceCommandService.deleteVoiceCommand(id);
        return ResponseEntity.noContent().build();
    }
}

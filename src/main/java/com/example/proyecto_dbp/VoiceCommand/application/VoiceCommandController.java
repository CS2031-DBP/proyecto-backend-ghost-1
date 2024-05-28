package com.example.proyecto_dbp.VoiceCommand.application;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDTO;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
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
    public List<VoiceCommand> getAllVoiceCommands() {
        return voiceCommandService.getAllVoiceCommands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoiceCommand> getVoiceCommandById(@PathVariable Long id) {
        VoiceCommand voiceCommand = voiceCommandService.getVoiceCommandById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VoiceCommand not found with id " + id));
        return ResponseEntity.ok(voiceCommand);
    }

    @GetMapping("/user/{userId}")
    public List<VoiceCommand> getVoiceCommandsByUserId(@PathVariable Long userId) {
        return voiceCommandService.getVoiceCommandsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<VoiceCommand> createVoiceCommand(@RequestBody VoiceCommand voiceCommand) {
        VoiceCommand savedVoiceCommand = voiceCommandService.createVoiceCommand(voiceCommand);
        return new ResponseEntity<>(savedVoiceCommand, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoiceCommand> updateVoiceCommand(@PathVariable Long id, @RequestBody VoiceCommand voiceCommand) {
        VoiceCommand updatedVoiceCommand = voiceCommandService.updateVoiceCommand(id, voiceCommand)
                .orElseThrow(() -> new ResourceNotFoundException("VoiceCommand not found with id " + id));
        return ResponseEntity.ok(updatedVoiceCommand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiceCommand(@PathVariable Long id) {
        voiceCommandService.deleteVoiceCommand(id);
        return ResponseEntity.noContent().build();
    }
}

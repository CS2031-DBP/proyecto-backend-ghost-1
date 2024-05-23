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
    public List<VoiceCommandDTO> getAllVoiceCommands() {return voiceCommandService.getAllVoiceCommands();}

    @GetMapping("/{id}")
    public ResponseEntity<VoiceCommandDTO> getVoiceCommandById(@PathVariable Long id) {
        return ResponseEntity.of(voiceCommandService.getVoiceCommandById(id));
    }

    @GetMapping("/user/{userId}")
    public List<VoiceCommandDTO> getVoiceCommandsByUserId(@PathVariable Long userId) {
        return voiceCommandService.getVoiceCommandsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<VoiceCommandDTO> createVoiceCommand(@RequestBody VoiceCommandDTO voiceCommandDTO) {
        VoiceCommandDTO createdVoiceCommand = voiceCommandService.createVoiceCommand(voiceCommandDTO);
        return new ResponseEntity<>(createdVoiceCommand, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoiceCommandDTO> updateVoiceCommand(@PathVariable Long id, @RequestBody VoiceCommandDTO voiceCommandDTO) {
        VoiceCommandDTO updatedVoiceCommand = voiceCommandService.updateVoiceCommand(id, voiceCommandDTO);
        return ResponseEntity.ok(updatedVoiceCommand);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVoiceCommand(@PathVariable Long id) {
        voiceCommandService.deleteVoiceCommand(id);
        return ResponseEntity.noContent().build();
    }
}

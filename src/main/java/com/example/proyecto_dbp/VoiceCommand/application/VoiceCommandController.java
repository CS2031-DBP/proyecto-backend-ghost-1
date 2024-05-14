package com.example.proyecto_dbp.VoiceCommand.application;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDto;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandInputDto;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import java.util.List;

@RestController
@RequestMapping("/api/voiceCommands")
public class VoiceCommandController {
    @Autowired
    private VoiceCommandService voiceCommandService;

    @PostMapping
    public ResponseEntity<VoiceCommandResponseDto> createVoiceCommand(@RequestBody VoiceCommandInputDto voiceCommandInputDto) {
        VoiceCommandDto commandDto = voiceCommandService.createVoiceCommand(voiceCommandInputDto);
        VoiceCommandResponseDto responseDto = new VoiceCommandResponseDto(commandDto.getId(), commandDto.getCommand());
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<VoiceCommandResponseDto>> getVoiceCommands() {
        List<VoiceCommandDto> commandDtos = voiceCommandService.getVoiceCommands();
        List<VoiceCommandResponseDto> responseDtos = VoiceCommandResponseDto.from(commandDtos);
        return ResponseEntity.ok(responseDtos);
    }
}

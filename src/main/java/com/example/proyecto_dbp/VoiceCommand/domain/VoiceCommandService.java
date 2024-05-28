package com.example.proyecto_dbp.VoiceCommand.domain;

import com.example.proyecto_dbp.VoiceCommand.infrastructure.VoiceCommandRepository;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoiceCommandService {

    @Autowired
    private VoiceCommandRepository voiceCommandRepository;

    public List<VoiceCommand> getAllVoiceCommands() {
        return voiceCommandRepository.findAll();
    }

    public Optional<VoiceCommand> getVoiceCommandById(Long id) {
        return voiceCommandRepository.findById(id);
    }

    public List<VoiceCommand> getVoiceCommandsByUserId(Long userId) {
        return voiceCommandRepository.findByUserId(userId);
    }

    public VoiceCommand createVoiceCommand(VoiceCommand voiceCommand) {
        return voiceCommandRepository.save(voiceCommand);
    }

    public Optional<VoiceCommand> updateVoiceCommand(Long id, VoiceCommand voiceCommand) {
        return voiceCommandRepository.findById(id)
                .map(existingCommand -> {
                    existingCommand.setCommand(voiceCommand.getCommand());
                    return voiceCommandRepository.save(existingCommand);
                });
    }

    public void deleteVoiceCommand(Long id) {
        VoiceCommand voiceCommand = voiceCommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("VoiceCommand not found with id " + id));
        voiceCommandRepository.delete(voiceCommand);
    }
}

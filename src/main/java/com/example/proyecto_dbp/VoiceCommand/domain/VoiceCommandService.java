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
        voiceCommand.setTimestamp(LocalDateTime.now());
        return voiceCommandRepository.save(voiceCommand);
    }

    public VoiceCommand updateVoiceCommand(Long id, VoiceCommand updatedVoiceCommand) {
        VoiceCommand voiceCommand = voiceCommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voice command not found with id " + id));

        voiceCommand.setCommand(updatedVoiceCommand.getCommand());
        voiceCommand.setDescriptionAction(updatedVoiceCommand.getDescriptionAction());
        voiceCommand.setUser(updatedVoiceCommand.getUser());
        voiceCommand.setActivity(updatedVoiceCommand.getActivity());

        return voiceCommandRepository.save(voiceCommand);
    }

    public void deleteVoiceCommand(Long id) {
        if (!voiceCommandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Voice command not found with id " + id);
        }
        voiceCommandRepository.deleteById(id);
    }
}

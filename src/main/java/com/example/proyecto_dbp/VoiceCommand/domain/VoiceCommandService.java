package com.example.proyecto_dbp.VoiceCommand.domain;

import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDTO;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import com.example.proyecto_dbp.VoiceCommand.infrastructure.VoiceCommandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VoiceCommandService {

    @Autowired
    private VoiceCommandRepository voiceCommandRepository;

    public List<VoiceCommandDTO> getAllVoiceCommands() {
        return voiceCommandRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    public Optional<VoiceCommandDTO> getVoiceCommandById(Long id) {
        return voiceCommandRepository.findById(id).map(this::convertToDTO);
    }

    public List<VoiceCommandDTO> getVoiceCommandsByUserId(Long userId) {
        return voiceCommandRepository.findAll().stream()
                .filter(voiceCommand -> voiceCommand.getUser().getId().equals(userId))
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public VoiceCommandDTO createVoiceCommand(VoiceCommandDTO voiceCommandDTO) {
        VoiceCommand voiceCommand = convertToEntity(voiceCommandDTO);
        voiceCommand = voiceCommandRepository.save(voiceCommand);
        return convertToDTO(voiceCommand);
    }

    public VoiceCommandDTO updateVoiceCommand(Long id, VoiceCommandDTO voiceCommandDTO) {
        VoiceCommand voiceCommand = voiceCommandRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Voice command not found with id " + id));

        voiceCommand.setCommand(voiceCommandDTO.getCommand());
        voiceCommand.setDescriptionAction(voiceCommandDTO.getDescriptionAction());
        voiceCommand.setUser(voiceCommandDTO.getUser());
        voiceCommand.setActivity(voiceCommandDTO.getActivity());

        voiceCommand = voiceCommandRepository.save(voiceCommand);
        return convertToDTO(voiceCommand);
    }

    public void deleteVoiceCommand(Long id) {
        if (!voiceCommandRepository.existsById(id)) {
            throw new ResourceNotFoundException("Voice command not found with id " + id);
        }
        voiceCommandRepository.deleteById(id);
    }

    private VoiceCommandDTO convertToDTO(VoiceCommand voiceCommand) {
        VoiceCommandDTO dto = new VoiceCommandDTO();
        dto.setId(voiceCommand.getId());
        dto.setCommand(voiceCommand.getCommand());
        dto.setDescriptionAction(voiceCommand.getDescriptionAction());
        dto.setTimestamp(voiceCommand.getTimestamp());
        dto.setUser(voiceCommand.getUser());
        dto.setActivity(voiceCommand.getActivity());
        return dto;
    }

    private VoiceCommand convertToEntity(VoiceCommandDTO voiceCommandDTO) {
        VoiceCommand voiceCommand = new VoiceCommand();
        voiceCommand.setId(voiceCommandDTO.getId());
        voiceCommand.setCommand(voiceCommandDTO.getCommand());
        voiceCommand.setDescriptionAction(voiceCommandDTO.getDescriptionAction());
        voiceCommand.setTimestamp(voiceCommandDTO.getTimestamp());
        voiceCommand.setUser(voiceCommandDTO.getUser());
        voiceCommand.setActivity(voiceCommandDTO.getActivity());
        return voiceCommand;
    }
}

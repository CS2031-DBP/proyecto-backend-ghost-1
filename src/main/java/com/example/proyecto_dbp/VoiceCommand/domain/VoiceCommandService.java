package com.example.proyecto_dbp.VoiceCommand.domain;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDTO;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

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
        Optional<VoiceCommand> optionalVoiceCommand = voiceCommandRepository.findById(id);
        if (optionalVoiceCommand.isPresent()) {
            VoiceCommand voiceCommand = optionalVoiceCommand.get();
            voiceCommand.setCommand(voiceCommandDTO.getComando());
            voiceCommand.setDescriptionAction(voiceCommandDTO.getDescripcionAccion());
            voiceCommand = voiceCommandRepository.save(voiceCommand);
            return convertToDTO(voiceCommand);
        }
        return null;
    }

    public void deleteVoiceCommand(Long id) {
        voiceCommandRepository.deleteById(id);
    }

    private VoiceCommandDTO convertToDTO(VoiceCommand voiceCommand) {
        VoiceCommandDTO voiceCommandDTO = new VoiceCommandDTO();
        voiceCommandDTO.setId(voiceCommand.getId());
        voiceCommandDTO.setComando(voiceCommand.getCommand());
        voiceCommandDTO.setDescripcionAccion(voiceCommand.getDescriptionAction());
        voiceCommandDTO.setUserId(voiceCommand.getUser().getId());
        if (voiceCommand.getActivity() != null) {
            voiceCommandDTO.setActivityId(voiceCommand.getActivity().getId());
        }
        return voiceCommandDTO;
    }

    private VoiceCommand convertToEntity(VoiceCommandDTO voiceCommandDTO) {
        VoiceCommand voiceCommand = new VoiceCommand();
        voiceCommand.setCommand(voiceCommandDTO.getComando());
        voiceCommand.setDescriptionAction(voiceCommandDTO.getDescripcionAccion());
        userRepository.findById(voiceCommandDTO.getUserId()).ifPresent(voiceCommand::setUser);
        if (voiceCommandDTO.getActivityId() != null) {
            activityRepository.findById(voiceCommandDTO.getActivityId()).ifPresent(voiceCommand::setActivity);
        }
        return voiceCommand;
    }
}

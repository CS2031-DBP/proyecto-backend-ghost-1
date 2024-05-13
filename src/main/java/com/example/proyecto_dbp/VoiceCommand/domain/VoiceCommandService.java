package com.example.proyecto_dbp.VoiceCommand.domain;

import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDto;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandInputDto;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.proyecto_dbp.VoiceCommand.infrastructure.VoiceCommandRepository;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import java.util.List;


@Service
public class VoiceCommandService {
    @Autowired
    private VoiceCommandRepository voiceCommandRepository;

    @Transactional
    public VoiceCommandDto createVoiceCommand(VoiceCommandInputDto inputDto) {
        VoiceCommand command = new VoiceCommand();
        command.setCommand(inputDto.getCommand());
        command = voiceCommandRepository.save(command);
        return new VoiceCommandDto(command.getId(), command.getCommand());
    }
}
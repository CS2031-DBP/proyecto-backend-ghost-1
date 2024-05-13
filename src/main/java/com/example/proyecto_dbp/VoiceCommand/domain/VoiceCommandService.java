package com.example.proyecto_dbp.VoiceCommand.domain;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.proyecto_dbp.VoiceCommand.infrastructure.VoiceCommandRepository;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import java.util.List;


@Service
public class VoiceCommandService {
    @Autowired
    private VoiceCommandRepository voiceCommandRepository;

    public List<VoiceCommand> getCommandsByUser(Long userId) {
        return voiceCommandRepository.findByUserId(userId);
    }

    // Más métodos de negocio
}

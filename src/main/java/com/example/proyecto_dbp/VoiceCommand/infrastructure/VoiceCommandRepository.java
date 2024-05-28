package com.example.proyecto_dbp.VoiceCommand.infrastructure;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VoiceCommandRepository extends JpaRepository<VoiceCommand, Long> {
    List<VoiceCommand> findByUserId(Long userId);
    VoiceCommand findByCommand(String command);
}

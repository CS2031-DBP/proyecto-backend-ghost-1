package com.example.proyecto_dbp.VoiceCommand.infrastructure;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoiceCommandRepository extends JpaRepository<VoiceCommand, Long> {
}

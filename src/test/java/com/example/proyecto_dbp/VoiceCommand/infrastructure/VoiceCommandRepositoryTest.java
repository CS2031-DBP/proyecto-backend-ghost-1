package com.example.proyecto_dbp.VoiceCommand.infrastructure;

import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import com.example.proyecto_dbp.VoiceCommand.dto.VoiceCommandDTO;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.Activity.domain.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class VoiceCommandRepositoryTest {

    @Autowired
    private VoiceCommandRepository voiceCommandRepository;

    @Autowired
    private VoiceCommandService voiceCommandService;

    private User testUser;
    private Activity testActivity;
    private VoiceCommandDTO testVoiceCommandDTO;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setRole("USER");

        testActivity = new Activity();
        testActivity.setTitulo("Test Activity");

        testVoiceCommandDTO = new VoiceCommandDTO();
        testVoiceCommandDTO.setCommand("Test Command");
        testVoiceCommandDTO.setDescriptionAction("Test Action");
        testVoiceCommandDTO.setTimestamp(LocalDateTime.now());
        testVoiceCommandDTO.setUser(testUser);
        testVoiceCommandDTO.setActivity(testActivity);
    }

    @Test
    void testSaveVoiceCommand() {
        VoiceCommandDTO savedVoiceCommandDTO = voiceCommandService.createVoiceCommand(testVoiceCommandDTO);

        assertNotNull(savedVoiceCommandDTO.getId());
        Optional<VoiceCommand> savedVoiceCommand = voiceCommandRepository.findById(savedVoiceCommandDTO.getId());
        assertTrue(savedVoiceCommand.isPresent());
        assertEquals(testVoiceCommandDTO.getCommand(), savedVoiceCommand.get().getCommand());
    }

    @Test
    void testGetVoiceCommandById() {
        VoiceCommandDTO savedVoiceCommandDTO = voiceCommandService.createVoiceCommand(testVoiceCommandDTO);
        Optional<VoiceCommandDTO> foundVoiceCommandDTO = voiceCommandService.getVoiceCommandById(savedVoiceCommandDTO.getId());

        assertTrue(foundVoiceCommandDTO.isPresent());
        assertEquals(savedVoiceCommandDTO.getCommand(), foundVoiceCommandDTO.get().getCommand());
    }

    @Test
    void testUpdateVoiceCommand() {
        VoiceCommandDTO savedVoiceCommandDTO = voiceCommandService.createVoiceCommand(testVoiceCommandDTO);

        savedVoiceCommandDTO.setCommand("Updated Command");
        VoiceCommandDTO updatedVoiceCommandDTO = voiceCommandService.updateVoiceCommand(savedVoiceCommandDTO.getId(), savedVoiceCommandDTO);

        assertNotNull(updatedVoiceCommandDTO);
        assertEquals("Updated Command", updatedVoiceCommandDTO.getCommand());

        Optional<VoiceCommand> updatedVoiceCommand = voiceCommandRepository.findById(updatedVoiceCommandDTO.getId());
        assertTrue(updatedVoiceCommand.isPresent());
        assertEquals("Updated Command", updatedVoiceCommand.get().getCommand());
    }

    @Test
    void testDeleteVoiceCommand() {
        VoiceCommandDTO savedVoiceCommandDTO = voiceCommandService.createVoiceCommand(testVoiceCommandDTO);

        voiceCommandService.deleteVoiceCommand(savedVoiceCommandDTO.getId());

        Optional<VoiceCommand> deletedVoiceCommand = voiceCommandRepository.findById(savedVoiceCommandDTO.getId());
        assertFalse(deletedVoiceCommand.isPresent());
    }
}

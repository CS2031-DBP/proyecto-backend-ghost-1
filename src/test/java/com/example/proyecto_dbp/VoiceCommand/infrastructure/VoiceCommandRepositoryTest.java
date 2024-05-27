package com.example.proyecto_dbp.VoiceCommand.infrastructure;

import com.example.proyecto_dbp.Activity.infrastructure.ActivityRepository;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommandService;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.Activity.domain.Activity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DataJpaTest
public class VoiceCommandRepositoryTest {

    @Autowired
    private VoiceCommandService voiceCommandService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    private User testUser;
    private Activity testActivity;
    private VoiceCommand testVoiceCommand;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password");
        testUser.setName("Test User");
        testUser.setRole("USER");
        userRepository.save(testUser);

        testActivity = new Activity();
        testActivity.setTitulo("Test Activity");
        activityRepository.save(testActivity);

        testVoiceCommand = new VoiceCommand();
        testVoiceCommand.setCommand("Test Command");
        testVoiceCommand.setDescriptionAction("Test Action");
        testVoiceCommand.setTimestamp(LocalDateTime.now());
        testVoiceCommand.setUser(testUser);
        testVoiceCommand.setActivity(testActivity);
    }

    @Test
    void testSaveVoiceCommand() {
        VoiceCommand savedVoiceCommand = voiceCommandService.createVoiceCommand(testVoiceCommand);

        assertNotNull(savedVoiceCommand.getId());
        Optional<VoiceCommand> foundVoiceCommand = voiceCommandService.getVoiceCommandById(savedVoiceCommand.getId());
        assertTrue(foundVoiceCommand.isPresent());
        assertEquals(testVoiceCommand.getCommand(), foundVoiceCommand.get().getCommand());
    }

    @Test
    void testGetVoiceCommandById() {
        VoiceCommand savedVoiceCommand = voiceCommandService.createVoiceCommand(testVoiceCommand);
        Optional<VoiceCommand> foundVoiceCommand = voiceCommandService.getVoiceCommandById(savedVoiceCommand.getId());

        assertTrue(foundVoiceCommand.isPresent());
        assertEquals(savedVoiceCommand.getCommand(), foundVoiceCommand.get().getCommand());
    }

    @Test
    void testUpdateVoiceCommand() {
        VoiceCommand savedVoiceCommand = voiceCommandService.createVoiceCommand(testVoiceCommand);
        savedVoiceCommand.setCommand("Updated Command");
        VoiceCommand updatedVoiceCommand = voiceCommandService.updateVoiceCommand(savedVoiceCommand.getId(), savedVoiceCommand);

        assertNotNull(updatedVoiceCommand);
        assertEquals("Updated Command", updatedVoiceCommand.getCommand());

        Optional<VoiceCommand> foundVoiceCommand = voiceCommandService.getVoiceCommandById(updatedVoiceCommand.getId());
        assertTrue(foundVoiceCommand.isPresent());
        assertEquals("Updated Command", foundVoiceCommand.get().getCommand());
    }

    @Test
    void testDeleteVoiceCommand() {
        VoiceCommand savedVoiceCommand = voiceCommandService.createVoiceCommand(testVoiceCommand);
        voiceCommandService.deleteVoiceCommand(savedVoiceCommand.getId());

        Optional<VoiceCommand> deletedVoiceCommand = voiceCommandService.getVoiceCommandById(savedVoiceCommand.getId());
        assertFalse(deletedVoiceCommand.isPresent());
    }
}

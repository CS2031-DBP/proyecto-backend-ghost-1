package com.example.proyecto_dbp.VoiceCommand.infrastructure;

import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class VoiceCommandRepositoryTest {

    @Autowired
    private VoiceCommandRepository voiceCommandRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;
    private Activity activity;
    private VoiceCommand voiceCommand;

    @BeforeEach
    public void setUp() {
        user = createUser();
        activity = createActivity();
        voiceCommand = createVoiceCommand(user, activity);
        entityManager.persist(user);
        entityManager.persist(activity);
        entityManager.persist(voiceCommand);
    }

    private User createUser() {
        User user = new User();
        user.setName("John");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        return user;
    }

    private Activity createActivity() {
        Activity activity = new Activity();
        activity.setTitulo("Running");
        activity.setDescripcion("Running in the park");
        return activity;
    }

    private VoiceCommand createVoiceCommand(User user, Activity activity) {
        return VoiceCommand.builder()
                .command("Play music")
                .descriptionAction("Play favorite music playlist")
                .timestamp(LocalDateTime.now())
                .user(user)
                .activity(activity)
                .build();
    }

    @Test
    public void testCreateVoiceCommand() {
        VoiceCommand newVoiceCommand = VoiceCommand.builder()
                .command("Turn off the lights")
                .descriptionAction("Turn off all lights in the house")
                .timestamp(LocalDateTime.now())
                .user(user)
                .activity(activity)
                .build();

        VoiceCommand savedVoiceCommand = voiceCommandRepository.save(newVoiceCommand);
        VoiceCommand retrievedVoiceCommand = entityManager.find(VoiceCommand.class, savedVoiceCommand.getId());

        assertVoiceCommandsEqual(newVoiceCommand, retrievedVoiceCommand);
    }

    @Test
    public void testFindById() {
        VoiceCommand retrievedVoiceCommand = voiceCommandRepository.findById(voiceCommand.getId()).orElse(null);

        assertNotNull(retrievedVoiceCommand);
        assertEquals(voiceCommand, retrievedVoiceCommand);
    }

    @Test
    public void testDeleteById() {
        voiceCommandRepository.deleteById(voiceCommand.getId());

        VoiceCommand retrievedVoiceCommand = entityManager.find(VoiceCommand.class, voiceCommand.getId());

        assertNull(retrievedVoiceCommand);
    }

    @Test
    public void testFindByUserId() {
        List<VoiceCommand> voiceCommands = voiceCommandRepository.findByUserId(user.getId());

        assertFalse(voiceCommands.isEmpty());
        assertEquals(voiceCommand, voiceCommands.get(0));
    }

    private void assertVoiceCommandsEqual(VoiceCommand expected, VoiceCommand actual) {
        assertEquals(expected.getCommand(), actual.getCommand());
        assertEquals(expected.getDescriptionAction(), actual.getDescriptionAction());
        assertEquals(expected.getTimestamp(), actual.getTimestamp());
        assertEquals(expected.getUser(), actual.getUser());
        assertEquals(expected.getActivity(), actual.getActivity());
    }
}
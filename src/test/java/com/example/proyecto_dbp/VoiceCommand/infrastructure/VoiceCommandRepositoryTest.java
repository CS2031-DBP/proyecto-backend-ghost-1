package com.example.proyecto_dbp.VoiceCommand.infrastructure;

import com.example.proyecto_dbp.TestLabE2e03Application;
import com.example.proyecto_dbp.VoiceCommand.domain.VoiceCommand;
import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.Course.domain.Course;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class VoiceCommandRepositoryTest extends TestLabE2e03Application {

    @Autowired
    private VoiceCommandRepository voiceCommandRepository;

    @Autowired
    private JpaRepository<User, Long> userRepository;

    @Autowired
    private JpaRepository<Activity, Long> activityRepository;

    @Autowired
    private JpaRepository<Course, Long> courseRepository;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setRoles("USER");
        userRepository.save(user);

        Course course = new Course();
        course.setNombreCurso("Course 1");
        course.setDescripcion("Description for Course 1");
        course.setProfesor("Professor 1");
        course.setUser(user);
        courseRepository.save(course);

        Activity activity1 = new Activity();
        activity1.setTitulo("Activity 1");
        activity1.setDescripcion("Description for Activity 1");
        activity1.setFechaInicio(new Date());
        activity1.setFechaFin(new Date());
        activity1.setEstado("ACTIVE");
        activity1.setAllDay(false);
        activity1.setLocation("Room 101");
        activity1.setOrganizer("Organizer 1");
        activity1.setReminder(true);
        activity1.setCourse(course);
        activityRepository.save(activity1);

        Activity activity2 = new Activity();
        activity2.setTitulo("Activity 2");
        activity2.setDescripcion("Description for Activity 2");
        activity2.setFechaInicio(new Date());
        activity2.setFechaFin(new Date());
        activity2.setEstado("ACTIVE");
        activity2.setAllDay(false);
        activity2.setLocation("Room 102");
        activity2.setOrganizer("Organizer 2");
        activity2.setReminder(false);
        activity2.setCourse(course);
        activityRepository.save(activity2);

        VoiceCommand command1 = new VoiceCommand();
        command1.setCommand("Turn on the lights");
        command1.setDescriptionAction("Turning on the lights in the living room");
        command1.setTimestamp(LocalDateTime.now());
        command1.setUser(user);
        command1.setActivity(activity1);

        VoiceCommand command2 = new VoiceCommand();
        command2.setCommand("Play music");
        command2.setDescriptionAction("Playing music in the living room");
        command2.setTimestamp(LocalDateTime.now());
        command2.setUser(user);
        command2.setActivity(activity2);

        voiceCommandRepository.save(command1);
        voiceCommandRepository.save(command2);
    }

    @AfterEach
    void tearDown() {
        voiceCommandRepository.deleteAll();
        userRepository.deleteAll();
        activityRepository.deleteAll();
        courseRepository.deleteAll();
    }

    @Test
    void testFindByCommand() {
        Optional<VoiceCommand> result = Optional.ofNullable(voiceCommandRepository.findByCommand("Turn on the lights"));
        assertThat(result).isPresent();
        assertThat(result.get().getCommand()).isEqualTo("Turn on the lights");
    }

    @Test
    void testFindByCommandNotFound() {
        Optional<VoiceCommand> result = Optional.ofNullable(voiceCommandRepository.findByCommand("Nonexistent command"));
        assertThat(result).isNotPresent();
    }
}

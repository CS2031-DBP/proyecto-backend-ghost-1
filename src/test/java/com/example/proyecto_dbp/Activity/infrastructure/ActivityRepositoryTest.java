package com.example.proyecto_dbp.Activity.infrastructure;

import static org.junit.jupiter.api.Assertions.*;
import com.example.proyecto_dbp.Activity.domain.Activity;
import com.example.proyecto_dbp.Course.domain.Course;
import com.example.proyecto_dbp.User.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.sql.Timestamp;

@DataJpaTest
public class ActivityRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ActivityRepository activityRepository;

    private Course course;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setName("Test User");
        user.setRoles("USER");
        user = entityManager.persist(user);

        course = new Course();
        course.setDescripcion("Descripción del curso");
        course.setNombreCurso("Curso de Prueba");
        course.setProfesor("Profesor X");
        course.setUser(user);
        course = entityManager.persist(course);
    }

    @Test
    public void testCreateActivity() {
        Activity activity = createActivity("Test Create Activity", false);
        Activity savedActivity = activityRepository.save(activity);
        assertNotNull(savedActivity);
        assertEquals("Test Create Activity", savedActivity.getTitulo());
    }

    @Test
    public void testFindById() {
        Activity activity = createActivity("Test Find By Id", true);
        Activity savedActivity = entityManager.persistFlushFind(activity);
        Activity foundActivity = activityRepository.findById(savedActivity.getId()).orElse(null);
        assertNotNull(foundActivity);
        assertEquals("Test Find By Id", foundActivity.getTitulo());
    }

    @Test
    public void testFindAllActivities() {
        entityManager.persist(createActivity("Activity One", true));
        entityManager.persist(createActivity("Activity Two", false));

        Iterable<Activity> activities = activityRepository.findAll();
        assertNotNull(activities);
        assertTrue(activities.iterator().hasNext());
    }

    @Test
    public void testDeleteActivity() {
        Activity activity = createActivity("Test Delete Activity", true);
        Activity savedActivity = entityManager.persistFlushFind(activity);
        assertNotNull(savedActivity);

        activityRepository.delete(savedActivity);
        Activity deletedActivity = activityRepository.findById(savedActivity.getId()).orElse(null);
        assertNull(deletedActivity);
    }

    private Activity createActivity(String title, boolean allDay) {
        Activity activity = new Activity();
        activity.setCourse(course);
        activity.setDescripcion("Descripción de la actividad " + title);
        activity.setEstado("Activo");
        activity.setFechaInicio(new Timestamp(System.currentTimeMillis()));
        activity.setFechaFin(new Timestamp(System.currentTimeMillis() + 86400000)); // más un día
        activity.setTitulo(title);
        return activity;
    }
}

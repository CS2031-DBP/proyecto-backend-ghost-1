package com.example.proyecto_dbp.User.infrastructure;

import com.example.proyecto_dbp.User.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private User user;

    @BeforeEach
    public void setUp() {
        user = createUser("test@example.com", "Test User", "password123", "STUDENT");
    }

    private User createUser(String email, String name, String password, String role) {
        User user = new User();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        user.setRoles(role);
        return entityManager.persist(user);
    }

    @Test
    public void testCreateUser() {
        User newUser = new User();
        newUser.setEmail("newuser@example.com");
        newUser.setName("New User");
        newUser.setPassword("newpassword");
        newUser.setRoles("STUDENT");

        User savedUser = userRepository.save(newUser);
        User retrievedUser = entityManager.find(User.class, savedUser.getId());

        assertEquals(newUser.getEmail(), retrievedUser.getEmail());
        assertEquals(newUser.getName(), retrievedUser.getName());
        assertEquals(newUser.getPassword(), retrievedUser.getPassword());
        assertEquals(newUser.getRoles(), retrievedUser.getRoles());
    }

    @Test
    public void testFindById() {
        Optional<User> retrievedUser = userRepository.findById(user.getId());

        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get());
    }

    @Test
    public void testFindByEmail() {
        Optional<User> retrievedUser = userRepository.findByEmail(user.getEmail());

        assertTrue(retrievedUser.isPresent());
        assertEquals(user, retrievedUser.get());
    }

    @Test
    public void testExistsByEmail() {
        boolean exists = userRepository.existsByEmail(user.getEmail());

        assertTrue(exists);
    }

    @Test
    public void testDeleteById() {
        userRepository.deleteById(user.getId());

        User retrievedUser = entityManager.find(User.class, user.getId());

        assertNull(retrievedUser);
    }

    @Test
    public void testUpdateUser() {
        user.setName("Updated User");
        user.setPassword("updatedpassword");

        User updatedUser = userRepository.save(user);
        User retrievedUser = entityManager.find(User.class, updatedUser.getId());

        assertEquals("Updated User", retrievedUser.getName());
        assertEquals("updatedpassword", retrievedUser.getPassword());
    }
}

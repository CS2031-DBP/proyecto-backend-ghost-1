package com.example.proyecto_dbp.User.application;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.dto.UserDTO;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        userRepository.save(user);
    }

    @Test
    public void testCreateUser() throws Exception {
        UserDTO newUser = new UserDTO();
        newUser.setName("Jane Doe");
        newUser.setEmail("jane.doe@example.com");

        var res = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        assert location != null;
        String id = location.substring(location.lastIndexOf("/") + 1);

        Optional<User> createdUser = userRepository.findById(Long.valueOf(id));
        Assertions.assertTrue(createdUser.isPresent());
        Assertions.assertEquals("Jane Doe", createdUser.get().getName());
        Assertions.assertEquals("jane.doe@example.com", createdUser.get().getEmail());
    }

    @Test
    public void testDeleteUser() throws Exception {
        UserDTO newUser = new UserDTO(null, "jane.doe@example.com", "Jane Doe", "password123", "USER");

        var res = mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newUser)))
                .andExpect(status().isCreated())
                .andReturn();

        String location = res.getResponse().getHeader("Location");
        assert location != null;
        String id = location.substring(location.lastIndexOf("/") + 1);

        Optional<User> createdUser = userRepository.findById(Long.valueOf(id));
        Assertions.assertTrue(createdUser.isPresent());

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());

        Assertions.assertFalse(userRepository.existsById(Long.valueOf(id)));
    }

    @Test
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john.doe@example.com"));
    }

    @Test
    public void testGetUserById() throws Exception {
        mockMvc.perform(get("/users/{id}", user.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john.doe@example.com"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        UserDTO updatedUser = new UserDTO();
        updatedUser.setName("Jane Doe");
        updatedUser.setEmail("jane.doe@example.com");

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane Doe"))
                .andExpect(jsonPath("$.email").value("jane.doe@example.com"));

        Optional<User> updatedUserEntity = userRepository.findById(user.getId());
        Assertions.assertTrue(updatedUserEntity.isPresent());
        Assertions.assertEquals("Jane Doe", updatedUserEntity.get().getName());
        Assertions.assertEquals("jane.doe@example.com", updatedUserEntity.get().getEmail());
    }
}

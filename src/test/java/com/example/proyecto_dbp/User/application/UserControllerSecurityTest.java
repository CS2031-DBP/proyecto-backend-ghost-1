package com.example.proyecto_dbp.User.application;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.domain.UserService;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.config.JwtService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setUp() throws Exception {
        userRepository.deleteAll();

        // Crea un usuario administrador y un usuario regular para las pruebas
        adminToken = createJwtToken("admin@example.com", "ADMIN");
        userToken = createJwtToken("user@example.com", "STUDENT");
    }

    private String createJwtToken(String email, String role) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setRole(role);
        userRepository.save(user);

        UserDetails userDetails = userService.userDetailsService().loadUserByUsername(email);
        return jwtService.generateToken(userDetails);
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccessToCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + adminToken)
                        .content("{\"email\":\"newuser@example.com\", \"name\":\"New User\", \"password\":\"password123\", \"role\":\"STUDENT\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    public void testStudentAccessToCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content("{\"email\":\"newuser@example.com\", \"name\":\"New User\", \"password\":\"password123\", \"role\":\"STUDENT\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccessToDeleteUser() throws Exception {
        User user = userRepository.findByEmail("user@example.com").orElseThrow();

        mockMvc.perform(delete("/users/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    public void testStudentAccessToDeleteUser() throws Exception {
        User user = userRepository.findByEmail("user@example.com").orElseThrow();

        mockMvc.perform(delete("/users/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccessToUpdateUser() throws Exception {
        User user = userRepository.findByEmail("user@example.com").orElseThrow();

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + adminToken)
                        .content("{\"email\":\"updateduser@example.com\", \"name\":\"Updated User\", \"password\":\"newpassword123\", \"role\":\"STUDENT\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    public void testStudentAccessToUpdateUser() throws Exception {
        User user = userRepository.findByEmail("user@example.com").orElseThrow();

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content("{\"email\":\"updateduser@example.com\", \"name\":\"Updated User\", \"password\":\"newpassword123\", \"role\":\"STUDENT\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void testAnonymousAccessToGetAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    public void testAdminAccessToGetAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + adminToken))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "STUDENT")
    public void testStudentAccessToGetAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }
}

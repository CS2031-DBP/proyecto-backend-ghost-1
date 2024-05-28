package com.example.proyecto_dbp.User.application;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.infrastructure.UserRepository;
import com.example.proyecto_dbp.config.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

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
    private JwtService jwtService;

    private String adminToken;
    private String userToken;

    @BeforeEach
    public void setUp() throws Exception {
        userRepository.deleteAll();
        adminToken = createJwtToken("admin@example.com", "ROLE_ADMIN", "Admin User");
        userToken = createJwtToken("user@example.com", "ROLE_STUDENT", "Student User");
    }

    private String createJwtToken(String email, String role, String name) {
        User user = new User();
        user.setEmail(email);
        user.setPassword("password");
        user.setRoles(role);
        user.setName(name);
        userRepository.save(user);

        UserDetails userDetails = userRepository.findByEmail(email).orElseThrow();
        return jwtService.generateToken(userDetails);
    }

    @Test
    public void testAdminAccessToCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + adminToken)
                        .content("{\"email\":\"newuser@example.com\", \"name\":\"New User\", \"password\":\"password123\", \"roles\":\"ROLE_STUDENT\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testStudentAccessToCreateUser() throws Exception {
        mockMvc.perform(post("/users")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content("{\"email\":\"newuser@example.com\", \"name\":\"New User\", \"password\":\"password123\", \"roles\":\"ROLE_STUDENT\"}"))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testStudentAccessToDeleteUser() throws Exception {
        User user = userRepository.findByEmail("user@example.com").orElseThrow();

        mockMvc.perform(delete("/users/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testStudentAccessToUpdateUser() throws Exception {
        User user = userRepository.findByEmail("user@example.com").orElseThrow();

        mockMvc.perform(put("/users/{id}", user.getId())
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken)
                        .content("{\"email\":\"updateduser@example.com\", \"name\":\"Updated User\", \"password\":\"newpassword123\", \"roles\":\"ROLE_STUDENT\"}"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testStudentAccessToGetAllUsers() throws Exception {
        mockMvc.perform(get("/users")
                        .contentType(APPLICATION_JSON)
                        .header("Authorization", "Bearer " + userToken))
                .andExpect(status().isForbidden());
    }
}

package com.example.proyecto_dbp.User.infrastructure;

import com.example.proyecto_dbp.User.domain.User;
import com.example.proyecto_dbp.User.domain.UserService;
import com.example.proyecto_dbp.User.dto.UserDTO;
import com.example.proyecto_dbp.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserRepositoryTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setName("John Doe");
        userDTO.setEmail("john.doe@example.com");
    }

    @Test
    public void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO createdUser = userService.createUser(userDTO);

        assertNotNull(createdUser);
        assertEquals(userDTO.getName(), createdUser.getName());
        assertEquals(userDTO.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testGetUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        Optional<UserDTO> foundUser = Optional.ofNullable(userService.getUserById(1L));

        assertTrue(foundUser.isPresent());
        assertEquals(userDTO.getName(), foundUser.get().getName());
        verify(userRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testUpdateUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO updatedDetails = new UserDTO();
        updatedDetails.setName("Jane Doe");
        updatedDetails.setEmail("jane.doe@example.com");

        UserDTO updatedUser = userService.updateUser(1L, updatedDetails);

        assertNotNull(updatedUser);
        assertEquals("Jane Doe", updatedUser.getName());
        assertEquals("jane.doe@example.com", updatedUser.getEmail());
        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testDeleteUser() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        doNothing().when(userRepository).delete(any(User.class));

        userService.deleteUser(1L);

        verify(userRepository, times(1)).findById(anyLong());
        verify(userRepository, times(1)).delete(any(User.class));

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {userService.deleteUser(1L);});

        assertEquals("User not found with id 1", exception.getMessage());
        verify(userRepository, times(2)).findById(anyLong());
        verify(userRepository, times(1)).delete(any(User.class));
    }
}

package com.example.crud_rest_api;
import com.example.crud_rest_api.domain.User;
import com.example.crud_rest_api.repository.UserRepository;
import com.example.crud_rest_api.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testGetAllUsers() {
        // Arrange
        List<User> expectedUsers = Arrays.asList(
                new User(1L, "John", "Doe", "john.doe@example.com", 30, true),
                new User(2L, "Jane", "Doe", "jane.doe@example.com", 25, true)
        );
        when(userRepository.findAll()).thenReturn(expectedUsers);

        // Act
        List<User> actualUsers = userService.getAllUsers();

        // Assert
        assertEquals(expectedUsers, actualUsers);
    }

    @Test
    public void testGetUserById() {
        // Arrange
        User expectedUser = new User(1L, "John", "Doe", "john.doe@example.com", 30, true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUserById(1L);

        // Assert
        assertEquals(expectedUser, actualUser);
    }

    @Test
    public void testCreateUser() {
        // Arrange
        User newUser = new User(null, "John", "Doe", "john.doe@example.com", 30, true);
        when(userRepository.save(any(User.class))).thenReturn(newUser);

        // Act
        User createdUser = userService.createUser(newUser);

        // Assert
        assertEquals(newUser, createdUser);
    }

    @Test
    public void testUpdateUser() {
        // Arrange
        User existingUser = new User(1L, "John", "Doe", "john.doe@example.com", 30, true);
        User updatedUser = new User(1L, "UpdatedJohn", "Doe", "updated.john.doe@example.com", 31, false);

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(1L, updatedUser);

        // Assert
        assertEquals(updatedUser, result);
    }

    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}


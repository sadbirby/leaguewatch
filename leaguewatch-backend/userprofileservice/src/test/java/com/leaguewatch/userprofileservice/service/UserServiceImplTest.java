package com.leaguewatch.userprofileservice.service;

import com.google.gson.Gson;
import com.leaguewatch.userprofileservice.exception.UserAlreadyExistsException;
import com.leaguewatch.userprofileservice.exception.UserNotFoundException;
import com.leaguewatch.userprofileservice.model.User;
import com.leaguewatch.userprofileservice.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private Gson gson;

    private static final String EMAIL_EXISTING = "existing@example.com";
    private static final String EMAIL_NEW = "new@example.com";
    private static final String USERNAME_EXISTING = "existingUser";
    private static final String PASSWORD = "password";

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);

        // Act
        List<User> result = userService.getAllUsers();

        // Assert
        assertEquals(userList, result);
    }

    @Test
    void getUserByEmail_existingUser_shouldReturnUser() throws UserNotFoundException {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(EMAIL_EXISTING);
        when(userRepository.findByEmail(EMAIL_EXISTING)).thenReturn(Optional.of(existingUser));

        // Act
        User result = userService.getUserByEmail(EMAIL_EXISTING);

        // Assert
        assertEquals(existingUser, result);
    }

    @Test
    void getUserByEmail_nonExistingUser_shouldThrowException() {
        // Arrange
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.getUserByEmail("nonexistent@example.com"));
    }

    @Test
    void addUser_validUser_shouldReturnSavedUser() throws UserAlreadyExistsException {
        // Arrange
        User newUser = new User();
        newUser.setEmail(EMAIL_NEW);
        when(userRepository.findByEmail(EMAIL_NEW)).thenReturn(Optional.empty());
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());
        when(userRepository.save(newUser)).thenReturn(newUser);

        // Act
        User result = userService.addUser(newUser);

        // Assert
        assertEquals(newUser, result);
        verify(kafkaTemplate, times(1)).send(any(), any());
    }

    @Test
    void addUser_userWithEmailAlreadyExists_shouldThrowException() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(EMAIL_EXISTING);
        when(userRepository.findByEmail(EMAIL_EXISTING)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(existingUser));
        verify(userRepository, never()).save(any());
        verify(kafkaTemplate, never()).send(any(), any());
    }

    @Test
    void addUser_userWithUsernameAlreadyExists_shouldThrowException() {
        // Arrange
        User existingUser = new User();
        existingUser.setUsername(USERNAME_EXISTING);
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepository.findByUsername(USERNAME_EXISTING)).thenReturn(Optional.of(existingUser));

        // Act & Assert
        assertThrows(UserAlreadyExistsException.class, () -> userService.addUser(existingUser));
        verify(userRepository, never()).save(any());
        verify(kafkaTemplate, never()).send(any(), any());
    }

    @Test
    void updateUser_existingUser_shouldReturnUpdatedUser() throws UserNotFoundException {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(EMAIL_EXISTING);
        User updatedUser = new User();
        updatedUser.setEmail(EMAIL_NEW);
        when(userRepository.findByEmail(EMAIL_EXISTING)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(EMAIL_EXISTING, updatedUser);

        // Assert
        assertEquals(updatedUser, result);
        verify(kafkaTemplate, times(1)).send(any(), any());
    }

    @Test
    void updateUser_nonExistingUser_shouldThrowException() {
        // Arrange
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.updateUser("nonexistent@example.com", new User()));
        verify(userRepository, never()).save(any());
        verify(kafkaTemplate, never()).send(any(), any());
    }

    @Test
    void deleteUser_existingUser_shouldReturnDeletedMessage() throws UserNotFoundException {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(EMAIL_EXISTING);
        when(userRepository.findByEmail(EMAIL_EXISTING)).thenReturn(Optional.of(existingUser));

        // Act
        String result = userService.deleteUser(EMAIL_EXISTING);

        // Assert
        assertEquals("Deleted: " + EMAIL_EXISTING, result);
        verify(userRepository, times(1)).deleteByEmail(EMAIL_EXISTING);
    }

    @Test
    void deleteUser_nonExistingUser_shouldThrowException() {
        // Arrange
        when(userRepository.findByEmail(any())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser("nonexistent@example.com"));
        verify(userRepository, never()).deleteByEmail(any());
    }
}

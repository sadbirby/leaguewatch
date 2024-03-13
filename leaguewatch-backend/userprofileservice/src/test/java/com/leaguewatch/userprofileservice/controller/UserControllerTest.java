package com.leaguewatch.userprofileservice.controller;

import com.leaguewatch.userprofileservice.exception.UserNotFoundException;
import com.leaguewatch.userprofileservice.model.User;
import com.leaguewatch.userprofileservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jakarta.validation.ValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    private static final String EMAIL_EXISTING = "existing@example.com";
    private static final String EMAIL_NEW = "new@example.com";
    private static final String USERNAME_EXISTING = "existingUser";

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers_shouldReturnListOfUsers() {
        // Arrange
        List<User> userList = new ArrayList<>();
        userList.add(new User(1, "user1", "user1@gmail.com", "password"));
        userList.add(new User(2, "user2", "user2@gmail.com", "password"));
        userList.add(new User(3, "user3", "user3@gmail.com", "password"));
        when(userService.getAllUsers()).thenReturn(userList);

        // Act
        ResponseEntity<Object> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals("Users retrieved", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void getUserByEmail_existingUser_shouldReturnUser() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(EMAIL_EXISTING);
        when(userService.getUserByEmail(EMAIL_EXISTING)).thenReturn(existingUser);

        // Act
        ResponseEntity<Object> response = userController.getUserByEmail(EMAIL_EXISTING);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingUser, ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals("User retrieved", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void getUserByEmail_nonExistingUser_shouldReturnNotFound() {
        // Arrange
        when(userService.getUserByEmail(any())).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.getUserByEmail("nonexistent@example.com"));
    }

    @Test
    void addUser_validUser_shouldReturnUserAdded() {
        // Arrange
        User newUser = new User();
        newUser.setEmail(EMAIL_NEW);
        when(userService.addUser(newUser)).thenReturn(newUser);

        // Act
        ResponseEntity<Object> response = userController.addUser(newUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(newUser, ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals("User added", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void addUser_invalidUser_shouldReturnBadRequest() {
        // Arrange
        User invalidUser = new User(); // Missing required fields to trigger validation exception
        when(userService.addUser(invalidUser)).thenThrow(new ValidationException("Invalid user"));

        // Act & Assert
        assertThrows(ValidationException.class, () -> userController.addUser(invalidUser));
    }

    @Test
    void updateUser_existingUser_shouldReturnUserUpdated() {
        // Arrange
        User existingUser = new User();
        existingUser.setEmail(EMAIL_EXISTING);
        User updatedUser = new User();
        updatedUser.setEmail(EMAIL_NEW);
        when(userService.updateUser(EMAIL_EXISTING, updatedUser)).thenReturn(updatedUser);

        // Act
        ResponseEntity<Object> response = userController.updateUser(EMAIL_EXISTING, updatedUser);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedUser, ((Map<?, ?>) response.getBody()).get("data"));
        assertEquals("User updated", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    void updateUser_nonExistingUser_shouldReturnNotFound() {
        // Arrange
        when(userService.updateUser(any(), any())).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.updateUser("nonexistent@example.com", new User()));
    }

    @Test
    void deleteUser_existingUser_shouldReturnUserDeleted() {
        // Arrange
        when(userService.deleteUser(EMAIL_EXISTING)).thenReturn("Deleted: " + EMAIL_EXISTING);

        // Act
        ResponseEntity<Object> response = userController.deleteUser(EMAIL_EXISTING);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted", ((Map<?, ?>) response.getBody()).get("message"));
        assertEquals("Deleted: " + EMAIL_EXISTING, ((Map<?, ?>) response.getBody()).get("data"));
    }

    @Test
    void deleteUser_nonExistingUser_shouldReturnNotFound() {
        // Arrange
        when(userService.deleteUser(any())).thenThrow(new UserNotFoundException("User not found"));

        // Act & Assert
        assertThrows(UserNotFoundException.class, () -> userController.deleteUser("nonexistent@example.com"));
    }
}

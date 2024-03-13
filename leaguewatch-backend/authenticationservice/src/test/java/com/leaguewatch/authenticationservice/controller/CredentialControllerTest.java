package com.leaguewatch.authenticationservice.controller;

import com.leaguewatch.authenticationservice.config.TokenGenerator;
import com.leaguewatch.authenticationservice.config.UserListener;
import com.leaguewatch.authenticationservice.model.Credential;
import com.leaguewatch.authenticationservice.service.CredentialService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CredentialControllerTest {

    @InjectMocks
    private CredentialController credentialController;

    @Mock
    private CredentialService credentialService;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private UserListener userListener;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserConsumer() {
        String credentialString = "{\"id\":1,\"username\":\"test\",\"email\":\"test@example.com\",\"password\":\"password\"}";
        Credential credential = new Credential();
        credential.setEmail("test@example.com");
        credential.setPassword("password");

        when(userListener.fetchUserCredential(any(String.class))).thenReturn(credential);
        when(credentialService.addOrUpdateCredential(any(Credential.class))).thenReturn("Added new user's credentials");
        assertEquals(credential, userListener.fetchUserCredential(credentialString));
        assertEquals("Added new user's credentials", credentialService.addOrUpdateCredential(credential));
    }

    @Test
    public void testLoginUser_ValidCredentials() {
        Credential credential = new Credential();
        credential.setEmail("test@example.com");
        credential.setPassword("password");

        when(credentialService.validateUser(any(Credential.class))).thenReturn(credential);
        when(tokenGenerator.generateToken(any(Credential.class))).thenReturn(anyMap());

        ResponseEntity<?> responseEntity = credentialController.loginUser(credential);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        Credential credential = new Credential();
        credential.setEmail("test@example.com");
        credential.setPassword("password");

        when(credentialService.validateUser(any(Credential.class))).thenReturn(null);

        ResponseEntity<?> responseEntity = credentialController.loginUser(credential);
        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
    }
}

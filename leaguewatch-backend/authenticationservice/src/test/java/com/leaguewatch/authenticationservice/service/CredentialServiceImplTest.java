package com.leaguewatch.authenticationservice.service;

import com.leaguewatch.authenticationservice.model.Credential;
import com.leaguewatch.authenticationservice.repository.CredentialRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CredentialServiceImplTest {
    @InjectMocks
    private CredentialServiceImpl credentialService;

    @Mock
    private CredentialRepository credentialRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddOrUpdateCredential_AddNewUser() {
        Credential credential = new Credential();
        credential.setEmail("test@example.com");
        credential.setPassword("password");

        when(credentialRepository.existsById(anyString())).thenReturn(false);
        credentialService.addOrUpdateCredential(credential);
        verify(credentialRepository, times(1)).save(any(Credential.class));
    }

    @Test
    public void testAddOrUpdateCredential_UpdateExistingUser() {
        Credential credential = new Credential();
        credential.setId("123");
        credential.setEmail("test@example.com");
        credential.setPassword("password");

        when(credentialRepository.existsById(anyString())).thenReturn(true);
        credentialService.addOrUpdateCredential(credential);
        verify(credentialRepository, times(1)).save(any(Credential.class));
    }

    @Test
    public void testValidateUser_ValidCredentials() {
        Credential credential = new Credential();
        credential.setEmail("test@example.com");
        credential.setPassword("password");

        when(credentialRepository.findByEmailAndPassword(anyString(), anyString()))
                .thenReturn(credential);

        Credential result = credentialService.validateUser(credential);
        assertEquals(credential, result);
    }

    @Test
    public void testValidateUser_InvalidCredentials() {
        Credential credential = new Credential();
        credential.setEmail("test@example.com");
        credential.setPassword("password");

        when(credentialRepository.findByEmailAndPassword(anyString(), anyString()))
                .thenReturn(null);

        Credential result = credentialService.validateUser(credential);
        assertEquals(null, result);
    }
}

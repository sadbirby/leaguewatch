package com.leaguewatch.authenticationservice.service;

import com.leaguewatch.authenticationservice.exception.UserNotFoundException;
import com.leaguewatch.authenticationservice.model.Credential;
import com.leaguewatch.authenticationservice.repository.CredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CredentialServiceImpl implements CredentialService {

    @Autowired
    CredentialRepository credentialRepository;

    @Override
    public String addOrUpdateCredential(Credential credential) {
        if (credentialRepository.existsById(credential.getId())) {
            credentialRepository.save(credential);
            return "Updated existing user's credentials";
        }
        credentialRepository.save(credential);
        return "Added new user's credentials";
    }

    @Override
    public Credential validateUser(Credential credential) {
        Credential result = credentialRepository.findByEmailAndPassword(credential.getEmail(), credential.getPassword());
        return result;
    }
}

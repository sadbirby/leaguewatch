package com.leaguewatch.authenticationservice.service;

import com.leaguewatch.authenticationservice.model.Credential;

public interface CredentialService {

    String addOrUpdateCredential(Credential credential);

    Credential validateUser(Credential credential);
}

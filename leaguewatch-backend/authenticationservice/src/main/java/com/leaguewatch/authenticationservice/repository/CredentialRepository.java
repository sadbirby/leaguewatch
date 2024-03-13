package com.leaguewatch.authenticationservice.repository;

import com.leaguewatch.authenticationservice.model.Credential;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CredentialRepository extends CrudRepository<Credential, String> {

    @Query("SELECT c FROM Credential c WHERE c.email = ?1 and c.password = ?2")
    public Credential findByEmailAndPassword(String email, String password);
}

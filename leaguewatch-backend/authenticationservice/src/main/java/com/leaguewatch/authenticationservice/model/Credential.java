package com.leaguewatch.authenticationservice.model;

import jakarta.persistence.*;

@Entity
public class Credential {
    @Id
    private String id;

    @Column(unique = true)
    private String email;

    private String password;

    public Credential() {

    }

    public Credential(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

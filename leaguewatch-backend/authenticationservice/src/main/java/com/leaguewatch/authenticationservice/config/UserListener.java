package com.leaguewatch.authenticationservice.config;

import com.google.gson.Gson;
import com.leaguewatch.authenticationservice.model.Credential;
import com.leaguewatch.authenticationservice.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserListener {

    Logger logger = LoggerFactory.getLogger(UserListener.class);

    @Autowired
    Gson gson;

    public Credential fetchUserCredential(String consumedUser) {
        User user = gson.fromJson(consumedUser, User.class);
        logger.info("New user entry detected. Consuming details for user: " + user.getEmail());

        Credential credential = new Credential();
        credential.setId(user.getId());
        credential.setEmail(user.getEmail());
        credential.setPassword(user.getPassword());

        return credential;
    }
}

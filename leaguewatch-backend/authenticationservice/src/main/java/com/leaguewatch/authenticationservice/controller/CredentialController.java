package com.leaguewatch.authenticationservice.controller;

import com.google.gson.Gson;
import com.leaguewatch.authenticationservice.config.TokenGenerator;
import com.leaguewatch.authenticationservice.config.UserListener;
import com.leaguewatch.authenticationservice.model.Credential;
import com.leaguewatch.authenticationservice.model.User;
import com.leaguewatch.authenticationservice.service.CredentialService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(path = "/api/v1/auth")
public class CredentialController {

    private static final String KAFKA_TOPIC = "leaguewatch-userdata-events";
    private static final String KAFKA_GROUP_ID = "group-leaguewatch";

    private Logger logger = LoggerFactory.getLogger(CredentialController.class);

    private CredentialService credentialService;
    private TokenGenerator tokenGenerator;
    private UserListener userListener;


    @Autowired
    public CredentialController(CredentialService credentialService, TokenGenerator tokenGenerator, UserListener userListener) {
        this.credentialService = credentialService;
        this.tokenGenerator = tokenGenerator;
        this.userListener = userListener;
    }

    @KafkaListener(topics = KAFKA_TOPIC, groupId = KAFKA_GROUP_ID)
    public void userConsumer(String consumedUser) {
        Credential credential = userListener.fetchUserCredential(consumedUser);
        String response = credentialService.addOrUpdateCredential(credential);
        logger.info(response);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody Credential credential) {
        Credential result = credentialService.validateUser(credential);

        if (result != null) {
            credential.setId(result.getId());
            Map<String, String> token = tokenGenerator.generateToken(credential);
            return new ResponseEntity<>(token, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid Credentials!", HttpStatus.UNAUTHORIZED);
        }
    }

}

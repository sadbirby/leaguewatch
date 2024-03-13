package com.leaguewatch.authenticationservice.config;

import com.leaguewatch.authenticationservice.model.Credential;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenGenerator {

    private static final String JWT_SECRET = "TfHbLdTbqNCWfWNEoguZ13EVer8U06eS";

    public Map<String, String> generateToken(Credential credential) {
        String jwtToken = tokenGenerator(credential);

        Map<String, String> jwtTokenMap = new HashMap<>();
        jwtTokenMap.put("message", "Login successful");
        jwtTokenMap.put("token", jwtToken);

        return jwtTokenMap;
    }

    private String tokenGenerator(Credential credential) {
        int timeInMs = 24 * 60 * 60 * 1000;
        Date now = new Date(System.currentTimeMillis());
        Date expiresIn = new Date(System.currentTimeMillis() + timeInMs);
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", credential.getId());

        return Jwts.builder()
                .setSubject(credential.getId())
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiresIn)
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET).compact();
    }

}

package com.leaguewatch.wishlistservice.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseHandler {

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObject) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("data", responseObject);
        return new ResponseEntity<>(map, status);
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, List<Object> responseObject) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("message", message);
        map.put("data", responseObject);
        return new ResponseEntity<>(map, status);
    }
}

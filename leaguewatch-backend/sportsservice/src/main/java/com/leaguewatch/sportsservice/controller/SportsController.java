package com.leaguewatch.sportsservice.controller;

import com.leaguewatch.sportsservice.response.ResponseHandler;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;

import com.leaguewatch.sportsservice.service.SportsService;

@RestController
@RequestMapping(path = "/api/v1/sports")
//@CircuitBreaker(name = "sports", fallbackMethod = "serviceDownFallback")
public class SportsController {

    @Autowired
    SportsService sportsService;

    @GetMapping("/league/all")
    public ResponseEntity<Object> getAllLeagues() {
        return ResponseHandler.generateResponse("Data retrieved successfully", HttpStatus.OK, sportsService.getAllLeagues());
    }

    @GetMapping("/league/{countryName}")
    public ResponseEntity<Object> getAllLeaguesByCountry(@PathVariable String countryName) {
        return ResponseHandler.generateResponse("Data retrieved successfully", HttpStatus.OK, sportsService.getAllLeaguesByCountry(countryName));
    }

    @GetMapping("/league/{countryName}/{sportName}")
    public ResponseEntity<Object> getAllLeaguesByCountry(@PathVariable String countryName, @PathVariable String sportName) {
        return ResponseHandler.generateResponse("Data retrieved successfully", HttpStatus.OK, sportsService.getAllLeaguesByCountryAndSport(countryName, sportName));
    }

    public ResponseEntity<String> serviceDownFallback(Exception exception) {
        return new ResponseEntity<String>("Service is down. Please wait while we fix it.", HttpStatus.SERVICE_UNAVAILABLE);
    }

}

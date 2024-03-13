package com.leaguewatch.sportsservice.controller;

import com.leaguewatch.sportsservice.model.Countries;
import com.leaguewatch.sportsservice.model.Leagues;
import com.leaguewatch.sportsservice.service.SportsService;
import com.leaguewatch.sportsservice.response.ResponseHandler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class SportsControllerTest {

    @InjectMocks
    private SportsController sportsController;

    @Mock
    private SportsService sportsService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllCountries() {
        // Arrange
        Leagues expectedCountries = new Leagues();
        when(sportsService.getAllLeagues()).thenReturn(expectedCountries);
        // Act
        ResponseEntity<Object> responseEntity = sportsController.getAllLeagues();
        // Assert
        assertEquals(ResponseHandler.generateResponse("Data retrieved successfully", HttpStatus.OK, expectedCountries), responseEntity);
    }

    @Test
    void getAllLeaguesByCountry() {
        // Arrange
        Countries expectedCountries = new Countries();
        when(sportsService.getAllLeaguesByCountry(anyString())).thenReturn(expectedCountries);
        // Act
        ResponseEntity<Object> responseEntity = sportsController.getAllLeaguesByCountry("SomeCountry");
        // Assert
        assertEquals(ResponseHandler.generateResponse("Data retrieved successfully", HttpStatus.OK, expectedCountries), responseEntity);
    }

    @Test
    void getAllLeaguesByCountryAndSport() {
        // Arrange
        Countries expectedCountries = new Countries();
        when(sportsService.getAllLeaguesByCountryAndSport(anyString(), anyString())).thenReturn(expectedCountries);
        // Act
        ResponseEntity<Object> responseEntity = sportsController.getAllLeaguesByCountry("SomeCountry", "SomeSport");
        // Assert
        assertEquals(ResponseHandler.generateResponse("Data retrieved successfully", HttpStatus.OK, expectedCountries), responseEntity);
    }

    @Test
    void serviceDownFallback() {
        // Arrange
        Exception exception = new Exception("Some exception message");
        // Act
        ResponseEntity<String> responseEntity = sportsController.serviceDownFallback(exception);
        // Assert
        assertEquals(new ResponseEntity<>("Service is down. Please wait while we fix it.", HttpStatus.SERVICE_UNAVAILABLE), responseEntity);
    }
}
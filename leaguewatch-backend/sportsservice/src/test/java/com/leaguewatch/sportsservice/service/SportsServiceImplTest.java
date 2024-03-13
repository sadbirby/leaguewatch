package com.leaguewatch.sportsservice.service;

import com.leaguewatch.sportsservice.model.Countries;
import com.leaguewatch.sportsservice.model.League;
import com.leaguewatch.sportsservice.model.Leagues;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class SportsServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private SportsServiceImpl sportsService;

    private Countries expectedCountries;
    private Leagues expectedLeagues;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);

        List<League> leagueList = new ArrayList<>();
        leagueList.add(new League("1", "Football", "Premier League", "2021-2022", "1872", "1888-12-02", "Male", "England", "The Premier League is the top tier of English football."));
        leagueList.add(new League("2", "Basketball", "NBA", "2021-2022", "1946", "1946-06-06", "Male", "United States", "The National Basketball Association is a professional basketball league."));
        leagueList.add(new League("3", "Soccer", "La Liga", "2021-2022", "1929", "1929-02-10", "Male", "Spain", "La Liga is the top professional football division of the Spanish football league system."));
        leagueList.add(new League("4", "Cricket", "Indian Premier League", "2021", "2008", "2008-04-18", "Male", "India", "The Indian Premier League is a professional Twenty20 cricket league."));
        leagueList.add(new League("5", "Rugby", "Six Nations Championship", "2022", "1883", "1883-03-27", "Male", "Various", "The Six Nations Championship is an annual international rugby union competition."));

        expectedCountries = new Countries(leagueList);
        expectedLeagues = new Leagues(leagueList);
    }

    @Test
    void getAllLeagues_Success() {
        // Arrange
        ResponseEntity<Leagues> responseEntity = new ResponseEntity<>(expectedLeagues, HttpStatus.OK);
        when(restTemplate.exchange(ArgumentMatchers.anyString(), ArgumentMatchers.any(HttpMethod.class), ArgumentMatchers.any(), ArgumentMatchers.<Class<Leagues>>any())).thenReturn(responseEntity);
        // Act
        Leagues result = sportsService.getAllLeagues();
        // Assert
        assertNotNull(result);
        assertEquals(expectedLeagues.getLeagues().get(0).getIdLeague(), result.getLeagues().get(0).getIdLeague());
//        assertTrue(new ReflectionEquals(expectedLeagues).matches(result));
    }

    @Test
    void getAllLeagues_Failure() {
        // Arrange
        when(restTemplate.exchange(anyString(), any(), any(), eq(Leagues.class))).thenThrow(new ResourceAccessException("Unable to access the resource!"));
        // Act & Assert
        assertThrows(ResourceAccessException.class, () -> sportsService.getAllLeagues());
    }

    @Test
    void getAllLeaguesByCountry_Success() {
        // Arrange
        Countries expectedCountries = new Countries();
        ResponseEntity<Countries> responseEntity = new ResponseEntity<>(expectedCountries, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Countries.class))).thenReturn(responseEntity);
        // Act
        Countries result = sportsService.getAllLeaguesByCountry("SomeCountry");
        // Assert
        assertNotNull(result);
        assertEquals(expectedCountries, result);
    }

    @Test
    void getAllLeaguesByCountry_Failure() {
        // Arrange
        when(restTemplate.exchange(anyString(), any(), any(), eq(Countries.class))).thenThrow(new ResourceAccessException("Unable to access the resource!"));
        // Act & Assert
        assertThrows(ResourceAccessException.class, () -> sportsService.getAllLeaguesByCountry("SomeCountry"));
    }

    @Test
    void getAllLeaguesByCountryAndSport_Success() {
        // Arrange
        Countries expectedCountries = new Countries();
        ResponseEntity<Countries> responseEntity = new ResponseEntity<>(expectedCountries, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Countries.class))).thenReturn(responseEntity);
        // Act
        Countries result = sportsService.getAllLeaguesByCountryAndSport("SomeCountry", "SomeSport");
        // Assert
        assertNotNull(result);
        assertEquals(expectedCountries, result);
    }

    @Test
    void getAllLeaguesByCountryAndSport_Failure() {
        // Arrange
        when(restTemplate.exchange(anyString(), any(), any(), eq(Countries.class))).thenThrow(new ResourceAccessException("Unable to access the resource!"));
        // Act & Assert
        assertThrows(ResourceAccessException.class, () -> sportsService.getAllLeaguesByCountryAndSport("SomeCountry", "SomeSport"));
    }

    @Test
    void getEntity() {
        // Act
        HttpEntity<?> entity = sportsService.getEntity();
        // Assert
        HttpHeaders headers = (HttpHeaders) ReflectionTestUtils.getField(entity, "headers");
        assertNotNull(headers);
        assertEquals(MediaType.APPLICATION_JSON_VALUE, headers.getFirst("Accept"));
    }
}
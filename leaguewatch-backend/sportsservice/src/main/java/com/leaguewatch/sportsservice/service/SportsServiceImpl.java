package com.leaguewatch.sportsservice.service;

import com.leaguewatch.sportsservice.model.Countries;
import com.leaguewatch.sportsservice.model.Leagues;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

@Service
public class SportsServiceImpl implements SportsService {

    public static final String EXT_API_ALL_LEAGUES = "https://thesportsdb.com/api/v1/json/3/all_leagues.php";
    public static final String EXT_API_ALL_LEAGUES_BY_COUNTRY_SPORT = "https://thesportsdb.com/api/v1/json/3/search_all_leagues.php?c=";


    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Leagues getAllLeagues() {
        ResponseEntity<Leagues> responseEntity = this.restTemplate.exchange(EXT_API_ALL_LEAGUES, HttpMethod.GET, getEntity(), Leagues.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) return responseEntity.getBody();
        else throw new ResourceAccessException("Unable to access the resource!");

    }

    @Override
    public Countries getAllLeaguesByCountry(String countryName) {
        String request = EXT_API_ALL_LEAGUES_BY_COUNTRY_SPORT + countryName;
        ResponseEntity<Countries> responseEntity = this.restTemplate.exchange(request, HttpMethod.GET, getEntity(), Countries.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) return responseEntity.getBody();
        else throw new ResourceAccessException("Unable to access the resource!");
    }

    @Override
    public Countries getAllLeaguesByCountryAndSport(String countryName, String sportName) {
        String request = EXT_API_ALL_LEAGUES_BY_COUNTRY_SPORT + countryName + "&s=" + sportName;
        ResponseEntity<Countries> responseEntity = this.restTemplate.exchange(request, HttpMethod.GET, getEntity(), Countries.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) return responseEntity.getBody();
        else throw new ResourceAccessException("Unable to access the resource!");
    }

    public HttpEntity<?> getEntity() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
        return new HttpEntity<>(headers);
    }

}

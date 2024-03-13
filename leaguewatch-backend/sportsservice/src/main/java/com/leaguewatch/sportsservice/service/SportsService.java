package com.leaguewatch.sportsservice.service;

import com.leaguewatch.sportsservice.model.Countries;
import com.leaguewatch.sportsservice.model.Leagues;

public interface SportsService {
    Leagues getAllLeagues();

    Countries getAllLeaguesByCountry(String countryName);

    Countries getAllLeaguesByCountryAndSport(String countryName, String sportName);

}

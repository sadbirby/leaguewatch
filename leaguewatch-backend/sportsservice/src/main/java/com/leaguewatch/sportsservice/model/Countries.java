package com.leaguewatch.sportsservice.model;

import java.util.List;

public class Countries {
    private List<League> countries;

    public Countries() {
    }

    public Countries(List<League> countries) {
        this.countries = countries;
    }

    public List<League> getCountries() {
        return countries;
    }

    public void setCountries(List<League> countries) {
        this.countries = countries;
    }
}

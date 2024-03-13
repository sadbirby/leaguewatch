package com.leaguewatch.sportsservice.model;

import java.util.List;

public class Leagues {
    private List<League> leagues;

    public Leagues() {

    }

    public Leagues(List<League> leagues) {
        this.leagues = leagues;
    }

    public List<League> getLeagues() {
        return leagues;
    }

    public void setLeagues(List<League> leagues) {
        this.leagues = leagues;
    }
}

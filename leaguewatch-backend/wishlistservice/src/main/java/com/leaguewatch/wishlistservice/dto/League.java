package com.leaguewatch.wishlistservice.dto;

public class League {

    private String idLeague;
    private String strSport;
    private String strLeague;
    private String strCurrentSeason;
    private String intFormedYear;
    private String dateFirstEvent;
    private String strGender;
    private String strCountry;
    private String strDescriptionEN;

    public League() {

    }

    public League(String idLeague, String strSport, String strLeague, String strCurrentSeason, String intFormedYear, String dateFirstEvent, String strGender, String strCountry, String strDescriptionEN) {
        this.idLeague = idLeague;
        this.strSport = strSport;
        this.strLeague = strLeague;
        this.strCurrentSeason = strCurrentSeason;
        this.intFormedYear = intFormedYear;
        this.dateFirstEvent = dateFirstEvent;
        this.strGender = strGender;
        this.strCountry = strCountry;
        this.strDescriptionEN = strDescriptionEN;
    }

    public String getIdLeague() {
        return idLeague;
    }

    public void setIdLeague(String idLeague) {
        this.idLeague = idLeague;
    }

    public String getStrSport() {
        return strSport;
    }

    public void setStrSport(String strSport) {
        this.strSport = strSport;
    }

    public String getStrLeague() {
        return strLeague;
    }

    public void setStrLeague(String strLeague) {
        this.strLeague = strLeague;
    }

    public String getStrCurrentSeason() {
        return strCurrentSeason;
    }

    public void setStrCurrentSeason(String strCurrentSeason) {
        this.strCurrentSeason = strCurrentSeason;
    }

    public String getIntFormedYear() {
        return intFormedYear;
    }

    public void setIntFormedYear(String intFormedYear) {
        this.intFormedYear = intFormedYear;
    }

    public String getDateFirstEvent() {
        return dateFirstEvent;
    }

    public void setDateFirstEvent(String dateFirstEvent) {
        this.dateFirstEvent = dateFirstEvent;
    }

    public String getStrGender() {
        return strGender;
    }

    public void setStrGender(String strGender) {
        this.strGender = strGender;
    }

    public String getStrCountry() {
        return strCountry;
    }

    public void setStrCountry(String strCountry) {
        this.strCountry = strCountry;
    }

    public String getStrDescriptionEN() {
        return strDescriptionEN;
    }

    public void setStrDescriptionEN(String strDescriptionEN) {
        this.strDescriptionEN = strDescriptionEN;
    }
}

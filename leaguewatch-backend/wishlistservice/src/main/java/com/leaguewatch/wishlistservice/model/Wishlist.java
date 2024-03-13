package com.leaguewatch.wishlistservice.model;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Wishlist {

    @Id
    private String id_league;
    private String strSport;
    private String strLeague;
    private String strCurrentSeason;
    private String intFormedYear;
    private String dateFirstEvent;
    private String strGender;
    private String strCountry;
    @Column(length = 4096)
    private String strDescriptionEN;
    @ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "wishlist_user", joinColumns = @JoinColumn(name = "wishlist_id"))
    private Set<String> users = new HashSet<>();

    public Wishlist() {
    }

    public Wishlist(String id_league, String strSport, String strLeague, String strCurrentSeason, String intFormedYear, String dateFirstEvent, String strGender, String strCountry, String strDescriptionEN, Set<String> users) {
        this.id_league = id_league;
        this.strSport = strSport;
        this.strLeague = strLeague;
        this.strCurrentSeason = strCurrentSeason;
        this.intFormedYear = intFormedYear;
        this.dateFirstEvent = dateFirstEvent;
        this.strGender = strGender;
        this.strCountry = strCountry;
        this.strDescriptionEN = strDescriptionEN;
        this.users = users;
    }

    public String getId_league() {
        return id_league;
    }

    public void setId_league(String id_league) {
        this.id_league = id_league;
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

    public Set<String> getUsers() {
        return users;
    }

    public void setUsers(Set<String> users) {
        this.users = users;
    }
}
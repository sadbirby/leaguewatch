package com.leaguewatch.wishlistservice.service;

import com.leaguewatch.wishlistservice.dto.League;
import com.leaguewatch.wishlistservice.exception.WishlistAlreadyExistsForUserException;
import com.leaguewatch.wishlistservice.exception.WishlistNotFoundException;
import com.leaguewatch.wishlistservice.model.Wishlist;
import com.leaguewatch.wishlistservice.repository.WishlistRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Override
    public Wishlist addToWishlist(String userId, League league) {
        Wishlist wishlist = wishlistRepository.findByIdLeague(league.getIdLeague());
        if (wishlist == null) {
            Wishlist newWishlist = new Wishlist();
            newWishlist.setId_league(league.getIdLeague());
            newWishlist.setStrSport(league.getStrSport());
            newWishlist.setStrLeague(league.getStrLeague());
            newWishlist.setStrCurrentSeason(league.getStrCurrentSeason());
            newWishlist.setIntFormedYear(league.getIntFormedYear());
            newWishlist.setDateFirstEvent(league.getDateFirstEvent());
            newWishlist.setStrGender(league.getStrGender());
            newWishlist.setStrCountry(league.getStrCountry());
            newWishlist.setStrDescriptionEN(league.getStrDescriptionEN());
            Set<String> userIds = new HashSet<>();
            userIds.add(userId);
            newWishlist.setUsers(userIds);
            return wishlistRepository.save(newWishlist);
        } else if (!wishlist.getUsers().contains(userId)) {
            wishlist.getUsers().add(userId);
            return wishlistRepository.save(wishlist);
        } else {
            throw new WishlistAlreadyExistsForUserException("You have already added this league to wishlist!");
        }

    }

    @Override
    public List<League> getWishlist(String userId) {
        List<Wishlist> wishlists = wishlistRepository.findByUserId(userId);
        List<League> leagues = new ArrayList<>();

        for (Wishlist wishlist : wishlists) {
            League league = new League();
            league.setIdLeague(wishlist.getId_league());
            league.setStrSport(wishlist.getStrSport());
            league.setStrLeague(wishlist.getStrLeague());
            league.setStrCurrentSeason(wishlist.getStrCurrentSeason());
            league.setIntFormedYear(wishlist.getIntFormedYear());
            league.setDateFirstEvent(wishlist.getDateFirstEvent());
            league.setStrGender(wishlist.getStrGender());
            league.setStrCountry(wishlist.getStrCountry());
            league.setStrDescriptionEN(wishlist.getStrDescriptionEN());
            leagues.add(league);
        }

        return leagues;
    }

    @Override
    public String deleteWishlist(String userId, String idLeague) {
        Wishlist optionalWishlist = wishlistRepository.findByIdLeague(idLeague);

        if (optionalWishlist != null) {
            Wishlist wishlist = optionalWishlist;
            wishlist.getUsers().remove(userId);

            if (wishlist.getUsers().isEmpty()) {
                wishlistRepository.delete(wishlist);
                return "Deleted wishlist";
            } else {
                wishlistRepository.save(wishlist);
                return "Deleted user from wishlist";
            }
        } else {
            throw new WishlistNotFoundException("Wishlist not found for the specified user and league");
        }
    }
}
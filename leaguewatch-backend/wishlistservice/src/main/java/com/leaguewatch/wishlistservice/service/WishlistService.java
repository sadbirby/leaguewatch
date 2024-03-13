package com.leaguewatch.wishlistservice.service;

import com.leaguewatch.wishlistservice.dto.League;
import com.leaguewatch.wishlistservice.model.Wishlist;

import java.util.List;

public interface WishlistService {

    Wishlist addToWishlist(String userId, League league);

    List<League> getWishlist(String userId);

    String deleteWishlist(String userId, String idLeague);
}

package com.leaguewatch.wishlistservice.controller;

import com.leaguewatch.wishlistservice.dto.League;
import com.leaguewatch.wishlistservice.model.Wishlist;
import com.leaguewatch.wishlistservice.service.WishlistService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WishlistControllerTest {

    @Mock
    private WishlistService wishlistService;

    @InjectMocks
    private WishlistController wishlistController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllLeaguesWishlistedByUser_Success() {
        String userId = "userId";
        List<League> leagues = Collections.singletonList(new League());
        when(wishlistService.getWishlist(userId)).thenReturn(leagues);

        ResponseEntity<Object> responseEntity = wishlistController.getAllLeaguesWishlistedByUser(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(wishlistService, times(1)).getWishlist(userId);
    }

    @Test
    void addLeagueToWishlist_Success() {
        String userId = "userId";
        League league = new League();
        when(wishlistService.addToWishlist(userId, league)).thenReturn(new Wishlist());

        ResponseEntity<Object> responseEntity = wishlistController.addLeagueToWishlist(userId, league);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(wishlistService, times(1)).addToWishlist(userId, league);
    }

    @Test
    void deleteLeagueFromWishlist_Success() {
        String userId = "userId";
        String idLeague = "123";
        when(wishlistService.deleteWishlist(userId, idLeague)).thenReturn("Deleted wishlist");

        ResponseEntity<Object> responseEntity = wishlistController.deleteLeagueFromWishlist(userId, idLeague);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        verify(wishlistService, times(1)).deleteWishlist(userId, idLeague);
    }
}
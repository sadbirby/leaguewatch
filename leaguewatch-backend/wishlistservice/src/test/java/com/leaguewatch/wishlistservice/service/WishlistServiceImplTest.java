package com.leaguewatch.wishlistservice.service;

import com.leaguewatch.wishlistservice.dto.League;
import com.leaguewatch.wishlistservice.exception.WishlistAlreadyExistsForUserException;
import com.leaguewatch.wishlistservice.exception.WishlistNotFoundException;
import com.leaguewatch.wishlistservice.model.Wishlist;
import com.leaguewatch.wishlistservice.repository.WishlistRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class WishlistServiceImplTest {

    @Mock
    private WishlistRepository wishlistRepository;

    @InjectMocks
    private WishlistServiceImpl wishlistService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addToWishlist_NewLeague_Success() {
        League league = new League();
        league.setIdLeague("123");
        Wishlist savedWishlist = new Wishlist();
        savedWishlist.setId_league(league.getIdLeague());
        when(wishlistRepository.findByIdLeague(league.getIdLeague())).thenReturn(null);
        when(wishlistRepository.save(any(Wishlist.class))).thenReturn(savedWishlist);

        Wishlist result = wishlistService.addToWishlist("userId", league);

        assertNotNull(result);
        assertEquals(league.getIdLeague(), result.getId_league());
        verify(wishlistRepository, times(1)).save(any(Wishlist.class));
    }

    @Test
    void addToWishlist_AlreadyExistsForUser_Exception() {
        League league = new League();
        league.setIdLeague("123");
        Wishlist existingWishlist = new Wishlist();
        existingWishlist.setId_league(league.getIdLeague());
        existingWishlist.setUsers(new HashSet<>(Collections.singletonList("userId")));
        when(wishlistRepository.findByIdLeague(league.getIdLeague())).thenReturn(existingWishlist);

        assertThrows(WishlistAlreadyExistsForUserException.class, () -> wishlistService.addToWishlist("userId", league));
    }

    // Additional tests for getWishlist and deleteWishlist methods
    @Test
    void testDeleteWishlist_SuccessfulRemoval() {
        String userId = "user123";
        String idLeague = "league123";

        Wishlist existingWishlist = new Wishlist();
        existingWishlist.setId_league(idLeague);
        existingWishlist.setUsers(new HashSet<>(Set.of(userId)));

        when(wishlistRepository.findByIdLeague(idLeague)).thenReturn(existingWishlist);
        when(wishlistRepository.save(any(Wishlist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = wishlistService.deleteWishlist(userId, idLeague);

        assertEquals("Deleted wishlist", result);
        assertTrue(existingWishlist.getUsers().isEmpty());
    }

    @Test
    void testDeleteWishlist_EmptyWishlist() {
        String userId = "user123";
        String idLeague = "league123";

        Wishlist existingWishlist = new Wishlist();
        existingWishlist.setId_league(idLeague);
        existingWishlist.setUsers(new HashSet<>(Set.of(userId)));

        when(wishlistRepository.findByIdLeague(idLeague)).thenReturn(existingWishlist);
        when(wishlistRepository.save(any(Wishlist.class))).thenAnswer(invocation -> invocation.getArgument(0));

        String result = wishlistService.deleteWishlist(userId, idLeague);

        assertEquals("Deleted wishlist", result);
        assertTrue(existingWishlist.getUsers().isEmpty());
    }

    @Test
    void testDeleteWishlist_WishlistNotFound() {
        String userId = "user123";
        String idLeague = "league123";

        when(wishlistRepository.findByIdLeague(idLeague)).thenReturn(null);

        WishlistNotFoundException exception = assertThrows(
                WishlistNotFoundException.class,
                () -> wishlistService.deleteWishlist(userId, idLeague)
        );

        assertEquals("Wishlist not found for the specified user and league", exception.getMessage());
    }
}


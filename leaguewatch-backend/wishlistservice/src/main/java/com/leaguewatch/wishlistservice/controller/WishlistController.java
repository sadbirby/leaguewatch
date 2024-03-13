package com.leaguewatch.wishlistservice.controller;

import com.leaguewatch.wishlistservice.dto.League;
import com.leaguewatch.wishlistservice.response.ResponseHandler;
import com.leaguewatch.wishlistservice.service.WishlistService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/wishlist")
public class WishlistController {

    @Autowired
    WishlistService wishlistService;

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getAllLeaguesWishlistedByUser(@PathVariable String userId) {
        return ResponseHandler.generateResponse("Wishlist data retrieved successfully", HttpStatus.OK, wishlistService.getWishlist(userId));
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity<Object> addLeagueToWishlist(@PathVariable String userId, @RequestBody League league) {
        return ResponseHandler.generateResponse("Wishlist data added successfully", HttpStatus.OK, wishlistService.addToWishlist(userId, league));
    }

    @DeleteMapping("/delete/{userId}/{idLeague}")
    public ResponseEntity<Object> deleteLeagueFromWishlist(@PathVariable String userId, @PathVariable String idLeague) {
        return ResponseHandler.generateResponse("Wishlist data deleted successfully", HttpStatus.OK, wishlistService.deleteWishlist(userId, idLeague));
    }
}

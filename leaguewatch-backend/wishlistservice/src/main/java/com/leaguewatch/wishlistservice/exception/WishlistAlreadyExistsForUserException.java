package com.leaguewatch.wishlistservice.exception;

public class WishlistAlreadyExistsForUserException extends RuntimeException {
    public WishlistAlreadyExistsForUserException(String message) {
        super(message);
    }
}

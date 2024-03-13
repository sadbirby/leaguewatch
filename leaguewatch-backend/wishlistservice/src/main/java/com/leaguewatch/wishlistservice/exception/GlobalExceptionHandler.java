package com.leaguewatch.wishlistservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundExceptionHandler(UserNotFoundException userNotFoundException) {
        logger.error("User not found", userNotFoundException);
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WishlistNotFoundException.class)
    public ResponseEntity<String> wishlistNotFoundExceptionHandler(WishlistNotFoundException wishlistNotFoundException) {
        logger.error("Wishlist not found", wishlistNotFoundException);
        return new ResponseEntity<>(wishlistNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WishlistAlreadyExistsForUserException.class)
    public ResponseEntity<String> wishlistAlreadyExistsForUserExceptionHandler(WishlistAlreadyExistsForUserException wishlistAlreadyExistsForUserException) {
        logger.error("Item already exists in wishlist", wishlistAlreadyExistsForUserException);
        return new ResponseEntity<>(wishlistAlreadyExistsForUserException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception exception) {
        logger.error("An exception occurred", exception);
        return new ResponseEntity<>("Encountered an exception", HttpStatus.CONFLICT);
    }
}

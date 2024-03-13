package com.leaguewatch.userprofileservice.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> userNotFoundExceptionHandler(UserNotFoundException userNotFoundException) {
        logger.error("User not found", userNotFoundException);
        return new ResponseEntity<>(userNotFoundException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<String> userAlreadyExistsExceptionHandler(UserAlreadyExistsException userAlreadyExistsException) {
        logger.error("User already exists");
        return new ResponseEntity<>(userAlreadyExistsException.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> validationExceptionHandler(MethodArgumentNotValidException methodArgumentNotValidException) {
        logger.error("Validation error", methodArgumentNotValidException);
        Map<String, String> errors = new HashMap<>();
        methodArgumentNotValidException.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<Map<String, String>>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> genericExceptionHandler(Exception exception) {
        logger.error("An exception occurred", exception);
        return new ResponseEntity<>("Encountered an exception", HttpStatus.CONFLICT);
    }
}

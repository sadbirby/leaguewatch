package com.leaguewatch.userprofileservice.controller;

import com.leaguewatch.userprofileservice.model.User;
import com.leaguewatch.userprofileservice.response.ResponseHandler;
import com.leaguewatch.userprofileservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/profile/all")
    public ResponseEntity<Object> getAllUsers() {
        return ResponseHandler.generateResponse("Users retrieved", HttpStatus.OK, userService.getAllUsers());
    }

    @GetMapping("/profile/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        return ResponseHandler.generateResponse("User retrieved", HttpStatus.OK, userService.getUserByEmail(email));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> addUser(@Valid @RequestBody User user) {
        return ResponseHandler.generateResponse("User added", HttpStatus.OK, userService.addUser(user));
    }

    @PutMapping("/profile/update/{email}")
    public ResponseEntity<Object> updateUser(@PathVariable String email, @Valid @RequestBody User user) {
        return ResponseHandler.generateResponse("User updated", HttpStatus.OK, userService.updateUser(email, user));
    }

    @DeleteMapping("/profile/delete/{email}")
    public ResponseEntity<Object> deleteUser(@PathVariable String email) {
        return ResponseHandler.generateResponse("User deleted", HttpStatus.OK, userService.deleteUser(email));
    }
}

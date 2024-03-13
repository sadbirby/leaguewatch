package com.leaguewatch.userprofileservice.service;

import com.leaguewatch.userprofileservice.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUserByEmail(String email);

    User addUser(User user);

    User updateUser(String email, User user);

    String deleteUser(String email);
}

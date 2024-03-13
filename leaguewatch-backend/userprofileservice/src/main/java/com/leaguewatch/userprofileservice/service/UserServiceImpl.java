package com.leaguewatch.userprofileservice.service;

import com.google.gson.Gson;
import com.leaguewatch.userprofileservice.exception.UserAlreadyExistsException;
import com.leaguewatch.userprofileservice.exception.UserNotFoundException;
import com.leaguewatch.userprofileservice.model.User;
import com.leaguewatch.userprofileservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private Gson gson;

    @Autowired
    UserRepository userRepository;

    private static final String KAFKA_TOPIC = "leaguewatch-userdata-events";

    @Override
    public List<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return (List<User>) users;
    }

    @Override
    public User getUserByEmail(String email) throws UserNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UserNotFoundException("User with email: " + email + " does not exist!");
        } else {
            return user.get();
        }
    }

    @Override
    public User addUser(User user) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("Another user with email: " + user.getEmail() + " already exists!");
        } else if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Another user with username: " + user.getUsername() + " already exists!");
        } else {
            User savedUser = userRepository.save(user);
            kafkaTemplate.send(KAFKA_TOPIC, gson.toJson(savedUser));
            return savedUser;
        }
    }

    @Override
    public User updateUser(String email, User user) throws UserNotFoundException {
        Optional<User> retrievedUser = userRepository.findByEmail(email);
        if (retrievedUser.isEmpty()) {
            throw new UserNotFoundException("User with email: " + email + " does not exist!");
        } else {
            User updatedUser = retrievedUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setEmail(user.getEmail());
            updatedUser.setPassword(user.getPassword());

            User savedUser = userRepository.save(updatedUser);
            kafkaTemplate.send(KAFKA_TOPIC, gson.toJson(savedUser));
            return savedUser;
        }
    }

    @Override
    public String deleteUser(String email) throws UserNotFoundException {
        Optional<User> retrievedUser = userRepository.findByEmail(email);
        if (retrievedUser.isEmpty()) {
            throw new UserNotFoundException("User with email: " + email + " does not exist!");
        } else {
            userRepository.deleteByEmail(email);
            return "Deleted: " + email;
        }
    }
}

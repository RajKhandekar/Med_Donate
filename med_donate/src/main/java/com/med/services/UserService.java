package com.med.services;

import java.util.List;
import java.util.Optional;

import com.med.entities.User;

public interface UserService {

    User saveUser(User user);

//optinal it specify If a value is present, isPresent() returns true. If no value is present, returns false.
    Optional<User> getUserById(String id);

    Optional<User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String userId);

    boolean isUserExistByEmail(String email);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    // add more methods here related user service[logic]

}
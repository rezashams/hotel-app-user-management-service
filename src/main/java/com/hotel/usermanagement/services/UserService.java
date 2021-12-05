/*
 * Copyright (c) 2021 Birmingham City University. All rights reserved.
 * Author:  Reza Shams (rezashams86@gmail.com)
 */
package com.hotel.usermanagement.services;

import com.hotel.usermanagement.model.User;

import java.util.List;

public interface UserService{

    List<User> findAll();

    User saveUser(User user);

    User getUserById(Long id);

    User updateUser(User user);

    void deleteUserById(Long id);

    boolean isUserAlreadyRegistered(String email);

    User signIn(String email, String password);

    boolean isManager(long userId);

    boolean isStudent(long userId);

}

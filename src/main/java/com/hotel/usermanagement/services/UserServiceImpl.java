/*
 * Copyright (c) 2021 Birmingham City University. All rights reserved.
 * Author:  Reza Shams (rezashams86@gmail.com)
 */

package com.hotel.usermanagement.services;

import com.hotel.usermanagement.model.User;
import com.hotel.usermanagement.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        Iterator<User> roomIterator= userRepository.findAll().iterator();
        while(roomIterator.hasNext()) {
            users.add(roomIterator.next());
        }
        return users;
    }

    @Override
    public User saveUser(User user) {
        User existUser = userRepository.findByEmail(user.getEmail());
        if(existUser !=null) return null;
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {

        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
      //  User existUser = userRepository.findByEmail(user.getEmail());
      //  if(existUser !=null) return null;
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public boolean isUserAlreadyRegistered(String email) {
        User user = userRepository.findByEmail(email);
        return user==null?false:true;
    }

    @Override
    public User signIn(String email, String password) {
        return userRepository.signIn(email, password);
    }

    @Override
    public boolean isManager(long userId) {
        User user = userRepository.findById(userId);
        if (user ==null) return false;
        return user.isManager()?true:false;
    }

    @Override
    public boolean isStudent(long userId) {
        User user = userRepository.findById(userId);
        if (user ==null) return false;
        return user.isStudent()?true:false;
    }
}

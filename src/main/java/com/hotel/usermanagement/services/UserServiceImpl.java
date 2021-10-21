/*
 * Copyright (c) 2021 Birmingham City University. All rights reserved.
 * Author:  Reza Shams (rezashams86@gmail.com)
 */

package com.hotel.usermanagement.services;

import com.hotel.usermanagement.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private  Map<Long, User> users = new HashMap<>();
    private  long userId=0;

    @Override
    public Set<User> findAll() {
        return  new HashSet<>(users.values());
    }

    @Override
    public User findById(Long userId) {
        return users.get(userId);
    }

    @Override
    public User save(User user) {
        if(user.getUserId()==0)
            user.setUserId(++userId);
        users.put(user.getUserId(),user);
        return user;
    }

    @Override
    public void delete(User user) {
       users.entrySet().removeIf(entry -> entry.getValue().equals(user));
    }

    @Override
    public void deleteById(Long userId) {
        //TODO implement it
    }
}

package com.hotel.usermanagement.services;

import com.hotel.usermanagement.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{

    private  Map<Long, User> users = new HashMap<>();
    private  long userId=0;

    @Override
    public Set<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long aLong) {
        return null;
    }

    @Override
    public User save(User user) {
        user.setUserId(++userId);
        users.put(user.getUserId(),user);
        return user;
    }

    @Override
    public void delete(User object) {

    }

    @Override
    public void deleteById(Long aLong) {

    }
}

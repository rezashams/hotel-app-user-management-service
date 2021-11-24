/*
 * Copyright (c) 2021 Birmingham City University. All rights reserved.
 * Author:  Reza Shams (rezashams86@gmail.com)
 */
package com.hotel.usermanagement.bootstrap;

import com.hotel.usermanagement.model.User;
import com.hotel.usermanagement.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Locale;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserService userService;

    public DataLoader(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setFirstName("reza");
        user.setLastName("shams");
        user.setEmail("rezashams86@gmail.com");
        user.setPassword("123");
        user.setStudent(true);
        user.setManager(true);
        userService.saveUser(user);
        System.out.println("Load Data ...");
    }
}

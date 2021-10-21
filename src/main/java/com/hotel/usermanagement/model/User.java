/*
 * Copyright (c) 2021 Birmingham City University. All rights reserved.
 * Author:  Reza Shams (rezashams86@gmail.com)
 */
package com.hotel.usermanagement.model;

import javax.persistence.*;

@Entity
@Table(name="articles")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="user_id")
    private long userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name="address")
    private String address;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @Column(name="role")
    private String role;

    public User() {}

    public User(String firstName, String lastName, String address, String email, String passowrd, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.email = email;
        this.password = passowrd;
        this.role = role;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String string) {
        this.password = string;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        return userId == user.userId;
    }

    @Override
    public int hashCode() {
        return (int) (userId ^ (userId >>> 32));
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    //Builder pattern
    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.role = builder.role;
        this.address = builder.address;
        this.email = builder.email;
    }

    public static class UserBuilder {
        private  String firstName;
        private  String lastName;
        private String password;
        private String address;
        private String email;
        private String role;

        public UserBuilder() {
        }

        public UserBuilder(String firstName, String lastName, String address, String email, String password, String role) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            this.address = address;
            this.email = email;
            this.role = role;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setAddress(String address) {
            this.address = address;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder setRole(String role) {
            this.role = role;
            return this;
        }
        public UserBuilder setPassword(String password) {
            this.password = password;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }

}

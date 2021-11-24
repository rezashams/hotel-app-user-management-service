/*
 * Copyright (c) 2021 Birmingham City University. All rights reserved.
 * Author:  Reza Shams (rezashams86@gmail.com)
 */
package com.hotel.usermanagement.model;

import javax.persistence.*;

@Entity(name="user")
@Table(name="user_")
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="id", nullable = false)
    private long userId;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;


    @Column(name="email", nullable = false)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Column(name="is_student", nullable = false)
    private  boolean isStudent;

    @Column(name="is_manager", nullable = false)
    private boolean isManager;

    public User() {}

    public User(String firstName, String lastName, String email,
                String password,boolean isManager, boolean isStudent) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.isManager = isManager;
        this.isStudent = isStudent;
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

    public boolean isStudent() {
        return isStudent;
    }

    public void setStudent(boolean student) {
        isStudent = student;
    }

    public boolean isManager() {
        return isManager;
    }

    public void setManager(boolean manager) {
        isManager = manager;
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
                '}';
    }

    //Builder pattern
    private User(UserBuilder builder) {
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.password = builder.password;
        this.isManager = builder.isManager;
        this.isStudent = builder.isStudent;
        this.email = builder.email;
    }

    public static class UserBuilder {
        private  String firstName;
        private  String lastName;
        private String password;
        private String email;
        private boolean isStudent;
        private boolean isManager;

        public UserBuilder() {
        }

        public UserBuilder(String firstName, String lastName, String email,
                           String password, boolean isStudent, boolean isManager) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.password = password;
            this.email = email;
            this.isManager= isManager;
            this.isStudent = isStudent;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setIsStudent(boolean isStudent) {
            this.isStudent = isStudent;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }
        public UserBuilder setIsManager(boolean isManager) {
            this.isManager = isManager;
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

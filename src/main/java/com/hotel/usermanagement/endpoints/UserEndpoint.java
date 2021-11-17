/*
 * Copyright (c) 2021 Birmingham City University. All rights reserved.
 * Author:  Reza Shams (rezashams86@gmail.com)
 */
package com.hotel.usermanagement.endpoints;

import com.hotel.usermanagement.model.User;
import com.hotel.usermanagement.services.UserService;
import com.hotelapp.xml.user.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Endpoint
public class UserEndpoint
{
    private static final String NAMESPACE_URI = "http://www.hotelapp.com/xml/user";
    //access to wsdl
    //http://localhost:8080/service/userWsdl.wsdl
    private UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUser(@RequestPayload GetUserByIdRequest request) {
        UserInfo userInfo = new UserInfo();
        User user =  userService.findById(request.getUserId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (user == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("User Not Available");
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User Deleted Successfully");
            BeanUtils.copyProperties(user, userInfo);
        }
        GetUserByIdResponse response = new GetUserByIdResponse();
        response.setServiceStatus(serviceStatus);
        response.setUserInfo(userInfo);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllUsersRequest")
    @ResponsePayload
    public GetAllUsersResponse getAllUsers() {
        GetAllUsersResponse response = new GetAllUsersResponse();
        List<UserInfo> userInfos = new ArrayList<>();
        Set<User> users = userService.findAll();
        for (User usr:users) {
            UserInfo userInfo = new UserInfo();
            BeanUtils.copyProperties(usr, userInfo);
            userInfos.add(userInfo);
        }
        response.getUserInfo().addAll(userInfos);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        UserInfo userInfo = request.getUserInfo();
        User user = new User.UserBuilder()
                .setFirstName(userInfo.getFirstName())
                .setLastName(userInfo.getLastName())
               // .setAddress(userInfo.getAddress()) TODO
                .setPassword(userInfo.getPassword())
                .setEmail(userInfo.getEmail())
              //  .setRole(userInfo.getRole()) TODO
                .build();
        user = userService.save(user);
        UserInfo userInfoResponse = new UserInfo();
        BeanUtils.copyProperties(user, userInfoResponse);
        response.setUserInfo(userInfoResponse);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("User Added Successfully");
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {

        UserInfo userInfo = request.getUserInfo();
        User user = new User.UserBuilder()
                .setFirstName(userInfo.getFirstName())
                .setLastName(userInfo.getLastName())
               // .setAddress(userInfo.getAddress()) TODO
                .setPassword(userInfo.getPassword())
                .setEmail(userInfo.getEmail())
                //.setRole(userInfo.getRole()) TODO
                .build();
        user.setUserId(userInfo.getUserId());
        userService.save(user);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("User Updated Successfully");
        UpdateUserResponse response = new UpdateUserResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        User user = userService.findById(request.getUserId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (user == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("User Not Available");
        } else {
            userService.delete(user);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User Deleted Successfully");
        }
        DeleteUserResponse response = new DeleteUserResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "greetUserRequest")
    @ResponsePayload
    public GreetUserResponse greetUser(@RequestPayload GreetUserRequest request) {
        GreetUserResponse response = new GreetUserResponse();
        GreetUserRes greetUserRes= new GreetUserRes();
        greetUserRes.setReturn("Welcome: "+request.getGreetUser().getArg0());
        response.setGreetUserResponse(greetUserRes);
        return response;
    }
}
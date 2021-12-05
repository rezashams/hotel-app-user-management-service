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
import java.util.Date;
import java.util.List;
import java.util.Set;

@Endpoint
public class UserEndpoint
{
    private static final String NAMESPACE_URI = "http://www.hotelapp.com/xml/user";
    //access to wsdl
    //http://localhost:9191/service/userWsdl.wsdl
    private UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getUserByIdRequest")
    @ResponsePayload
    public GetUserByIdResponse getUser(@RequestPayload GetUserByIdRequest request) {
        UserInfo userInfo = new UserInfo();
        User user =  userService.getUserById(request.getUserId());
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
        List<User> users = userService.findAll();
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
        ServiceStatus serviceStatus = new ServiceStatus();
        try {
            UserInfo userInfo = request.getUserInfo();
            User user = new User.UserBuilder()
                .setFirstName(userInfo.getFirstName())
                .setLastName(userInfo.getLastName())
                .setPassword(userInfo.getPassword())
                .setEmail(userInfo.getEmail())
                .setIsStudent(userInfo.isStudent())
                .setIsManager(userInfo.isManager())
                .setLastUpdate(new Date())
                .build();
            User existUser = userService.saveUser(user);
            if (existUser==null) {
                serviceStatus.setStatusCode("FAILED");
                serviceStatus.setMessage("The email has been registered before!");
            } else {
                UserInfo userInfoResponse = new UserInfo();
                BeanUtils.copyProperties(user, userInfoResponse);
                response.setUserInfo(userInfoResponse);
                serviceStatus.setStatusCode("SUCCESS");
                serviceStatus.setMessage("User Added Successfully");
            }
        } catch (Exception e) {
        serviceStatus.setStatusCode("FAIL");
        serviceStatus.setMessage("Server Error 500!");
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {

        UserInfo userInfo = request.getUserInfo();
        ServiceStatus serviceStatus = new ServiceStatus();
        try{
        User user = new User.UserBuilder()
                .setFirstName(userInfo.getFirstName())
                .setLastName(userInfo.getLastName())
                .setPassword(userInfo.getPassword())
                .setEmail(userInfo.getEmail())
                .setIsManager(userInfo.isManager())
                .setIsStudent(userInfo.isStudent())
                .setLastUpdate(new Date())
                .build();
        user.setUserId(userInfo.getUserId());
        User existUser = userService.updateUser(user);
        if (existUser==null) {
            serviceStatus.setStatusCode("FAILED");
            serviceStatus.setMessage("The email has been registered before!");
        } else {
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User Updated Successfully");
        }
    } catch (Exception e) {
        serviceStatus.setStatusCode("FAIL");
        serviceStatus.setMessage("Server Error 500!");
    }

        UpdateUserResponse response = new UpdateUserResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        User user = userService.getUserById(request.getUserId());
        ServiceStatus serviceStatus = new ServiceStatus();
        DeleteUserResponse response = new DeleteUserResponse();
        try {
            if (user == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("User Not Available");
            } else {
            userService.deleteUserById(user.getUserId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User Deleted Successfully");
            }
        } catch (Exception e) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Server Error 500!");
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "isUserAlreadyRegisteredRequest")
    @ResponsePayload
    public IsUserAlreadyRegisteredResponse isUserAlreadyRegistered(@RequestPayload IsUserAlreadyRegisteredRequest request) {
        ServiceStatus serviceStatus = new ServiceStatus();
        IsUserAlreadyRegisteredResponse response = new IsUserAlreadyRegisteredResponse();
        try {
            boolean res= userService.isUserAlreadyRegistered(request.getEmail());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("SUCCESS");
            response.setIsRegisteredBefore(res);
        } catch (Exception e){
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Server Error 500!");

        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "signInRequest")
    @ResponsePayload
    public SignInResponse signIn(@RequestPayload SignInRequest request) {
        ServiceStatus serviceStatus = new ServiceStatus();
        SignInResponse response = new SignInResponse();
        try {
            User user= userService.signIn(request.getEmail(), request.getPassword());
            if(user==null) {
                serviceStatus.setStatusCode("FAIL");
                serviceStatus.setMessage("User or Password  is not correct");
                response.setServiceStatus(serviceStatus);
                return response;
            }
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("SUCCESS");
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getUserId());
            userInfo.setFirstName(user.getFirstName());
            userInfo.setLastName(user.getLastName());
            userInfo.setEmail(user.getEmail());
            userInfo.setPassword(user.getPassword());
            userInfo.setManager(user.isManager());
            userInfo.setStudent(user.isStudent());
            response.setUserInfo(userInfo);
            user.setLastUpdate(new Date());
            userService.updateUser(user);
        } catch (Exception e){
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Server Error 500!");
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "isManagerRequest")
    @ResponsePayload
    public IsManagerResponse isManager(@RequestPayload IsManagerRequest request) {
        ServiceStatus serviceStatus = new ServiceStatus();
        IsManagerResponse response = new IsManagerResponse();
        try {
            boolean res= userService.isManager(request.getUserId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("SUCCESS");
            response.setIsManager(res);
        } catch (Exception e){
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Server Error 500!");

        }
        response.setServiceStatus(serviceStatus);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "isStudentRequest")
    @ResponsePayload
    public IsStudentResponse isStudent(@RequestPayload IsStudentRequest request) {
        ServiceStatus serviceStatus = new ServiceStatus();
        IsStudentResponse response = new IsStudentResponse();
        try {
            boolean res= userService.isStudent(request.getUserId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("SUCCESS");
            response.setIsStudent(res);
        } catch (Exception e){
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("Server Error 500!");

        }
        response.setServiceStatus(serviceStatus);
        return response;
    }


}
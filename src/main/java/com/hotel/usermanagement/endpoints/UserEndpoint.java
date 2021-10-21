package com.hotel.usermanagement.endpoints;

import com.hotel.usermanagement.model.User;
import com.hotel.usermanagement.services.UserService;
import com.hotelapp.xml.user.AddUserRequest;
import com.hotelapp.xml.user.AddUserResponse;
import com.hotelapp.xml.user.ServiceStatus;
import com.hotelapp.xml.user.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class UserEndpoint
{
    private static final String NAMESPACE_URI = "http://www.hotelapp.com/xml/user";

    private UserService userService;

    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "addUserRequest")
    @ResponsePayload
    public AddUserResponse addUser(@RequestPayload AddUserRequest request) {
        AddUserResponse response = new AddUserResponse();
        UserInfo userInfo = request.getUserInfo();
        User user = new User.UserBuilder()
                .setFirstName(userInfo.getFirstName())
                .setLastName(userInfo.getLastName())
                .setAddress(userInfo.getAddress())
                .setPassword(userInfo.getPassword())
                .setEmail(userInfo.getEmail())
                .setRole(userInfo.getRole())
                .build();
        user = userService.save(user);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("Content Added Successfully");
        response.setServiceStatus(serviceStatus);
        response.setUserId(user.getUserId());
        System.out.println("userId is:" + user.getUserId());
        return response;
    }
}
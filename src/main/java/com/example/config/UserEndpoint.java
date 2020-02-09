package com.example.config;

import com.soapexample.xml.UserRequest;
import com.soapexample.xml.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * Created by Diana Aimbetova on 2/9/2020
 **/
@Endpoint
public class UserEndpoint {
    private static final String NAMESPACE_URI = "http://www.soapexample.com/xml";
    private UserService userService;
    @Autowired
    public UserEndpoint(UserService userService) {
        this.userService = userService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UserRequest")
    @ResponsePayload
    public UserResponse getUser(@RequestPayload UserRequest request) {
        UserResponse response = new UserResponse();
        response.setUser(UserService.findUser(request.getUserId()));

        return response;
    }

}

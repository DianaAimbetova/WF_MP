package com.example.config;

import com.soapexample.xml.*;
import org.springframework.beans.BeanUtils;
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
        response.setUser(userService.findUser(request.getUserId()));

        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetAllUserRequest")
    @ResponsePayload
    public GetAllUserResponse getAllUser(@RequestPayload GetAllUserRequest request) {
        GetAllUserResponse response = new GetAllUserResponse();
        response.getUser().addAll(userService.findAllUsers());
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateUserRequest")
    @ResponsePayload
    public CreateUserResponse createUser(@RequestPayload CreateUserRequest request) {
        CreateUserResponse response = new CreateUserResponse();
        ServiceStatus serviceStatus = new ServiceStatus();
        User user = new User();
        user.setUserId(request.getUserId());
        user.setUserName(request.getUserName());
        user.setUserSurname(request.getUserSurname());
        user.setMail(request.getMail());
        Boolean flag = userService.create(user);
        if (!flag) {
            serviceStatus.setStatusCode("CONFLICT");
            serviceStatus.setMessage("User already exists");
        } else {
            User user1 = new User();
            BeanUtils.copyProperties(user, user1);
            response.setUser(user1);
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User added successfully");
        }
        response.setServiceStatus(serviceStatus);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "UpdateUserRequest")
    @ResponsePayload
    public UpdateUserResponse updateUser(@RequestPayload UpdateUserRequest request) {
        User user = new User();
        BeanUtils.copyProperties(request.getUser(), user);
        userService.update(user.getUserId(),user);
        ServiceStatus serviceStatus = new ServiceStatus();
        serviceStatus.setStatusCode("SUCCESS");
        serviceStatus.setMessage("User updated successfully");
        UpdateUserResponse response = new UpdateUserResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "DeleteUserRequest")
    @ResponsePayload
    public DeleteUserResponse deleteUser(@RequestPayload DeleteUserRequest request) {
        User user = userService.findUser(request.getUserId());
        ServiceStatus serviceStatus = new ServiceStatus();
        if (user == null ) {
            serviceStatus.setStatusCode("FAIL");
            serviceStatus.setMessage("User is not available");
        } else {
            userService.delete(user.getUserId());
            serviceStatus.setStatusCode("SUCCESS");
            serviceStatus.setMessage("User deleted successfully");
        }
        DeleteUserResponse response = new DeleteUserResponse();
        response.setServiceStatus(serviceStatus);
        return response;
    }

}

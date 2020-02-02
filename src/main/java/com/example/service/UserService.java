package com.example.service;

import com.example.entity.User;

import java.util.List;

public interface UserService {
    List<User> allUsers();

    User getUserDetail(final String id);

    User create(String userId, String username, String userSurname, String email);

    User update(String id, User student);

    void delete(String id);
}

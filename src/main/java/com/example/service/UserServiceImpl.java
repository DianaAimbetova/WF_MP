package com.example.service;

import com.example.entity.Task;
import com.example.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private ConcurrentMap<String, User> userMap;

    public UserServiceImpl() {

        final User userOne = new User("11", "Adam", "Smith", "adam.smith@mail.com");
        final User userTwo = new User("22", "James", "Driver", "james.driver@mail.com");
        final User userThree = new User("33", "Kate", "Jackson", "kate.jackson@mail.com");

        HashMap<String, Task> userOneTaskMap = new HashMap<>();
        HashMap<String, Task> userTwoTaskMap = new HashMap<>();
        HashMap<String, Task> userThreeTaskMap = new HashMap<>();

        userOneTaskMap.put("111", new Task("111", "task1", "task 1 description", java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()), java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(1))));
        userOneTaskMap.put("222", new Task("222", "task2", "task 2 description", java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()), java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(2))));

        userTwoTaskMap.put("333", new Task("333", "task3", "task 3 description", java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()), java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(3))));
        userTwoTaskMap.put("444", new Task("444", "task4", "task 4 description", java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()), java.sql.Date.valueOf(new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().plusDays(4))));

        userOne.setTasks(userOneTaskMap);
        userTwo.setTasks(userTwoTaskMap);

        userMap = Arrays.asList(
                new User[]{
                        userOne,
                        userTwo,
                        userThree,
                }).stream()
                .collect(Collectors.toConcurrentMap(s -> s.getUserId(), Function.identity()));


    }

    @Bean(name = "userMap")
    public ConcurrentMap<String, User> getUserMap() {
        return userMap;
    }

    @Override
    public List<User> allUsers() {
        return new ArrayList<>(userMap.values());
    }

    @Override
    public User getUserDetail(final String customerId) {
        return userMap.get(customerId);
    }

    @Override
    public User create(String userId, String username, String userSurname, String email) {
        User user = new User(userId, username, userSurname, email);
        userMap.put(user.getUserId(), user);
        return user;
    }

    @Override
    public User update(String id, User user) {
        user.setUserId(id);
        User oldUser = userMap.replace(id, user);
        return oldUser == null ? null : user;
    }

    @Override
    public void delete(String id) {
        userMap.remove(id);
    }
}


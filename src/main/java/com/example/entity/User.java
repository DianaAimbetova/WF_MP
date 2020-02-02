package com.example.entity;

/**
 * Created by Diana Aimbetova on 2/2/2020
 **/

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.hateoas.ResourceSupport;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User extends ResourceSupport {
    private String userId;
    private String userName;
    private String userSurname;
    private String mail;
    private Map<String, Task> tasks = new HashMap<>();

    public User() {
        super();
    }

    public User(String userId, String userName, String userSurname, String mail) {
        super();
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.mail = mail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public Map<String, Task> getTasks() {
        return tasks;
    }

    public void setTasks(Map<String, Task> tasks) {
        this.tasks = tasks;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public boolean hasTask() {
        if(tasks.isEmpty()){
            return false;
        }else{
            return true;
        }
    }
}

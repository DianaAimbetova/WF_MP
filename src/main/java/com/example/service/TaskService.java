package com.example.service;

import com.example.entity.Task;

import java.util.Collection;

public interface TaskService {

    Collection<Task> getAllTasksForUser(String userId);

    Task getTaskByIdForUser(String userId, String taskId);

}

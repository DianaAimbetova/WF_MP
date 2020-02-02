package com.example.service;

import com.example.entity.Task;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private Map<String, User> userMap;


    @Override
    public List<Task> getAllTasksForUser(final String userId) {
            return new ArrayList<>(userMap.get(userId).getTasks().values());

    }

    @Override
    public Task getTaskByIdForUser(final String userId, final String orderId) {
        final Map<String, Task> tasks = userMap.get(userId).getTasks();
        Task selectedTask = tasks.get(orderId);
        return selectedTask;
    }

}

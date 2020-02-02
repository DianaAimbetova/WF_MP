package com.example.controller;

import com.example.entity.Task;
import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.service.TaskService;
import com.example.service.UserService;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/users")
@EnableHypermediaSupport(type = HypermediaType.HAL)
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable final String userId) {
        return userService.getUserDetail(userId);
    }

    @GetMapping("/{userId}/task/{taskId}")
    public Task getTaskById(@PathVariable final String userId, @PathVariable final String taskId) {
        return taskService.getTaskByIdForUser(userId, taskId);
    }

    @GetMapping(value = "/{userId}/tasks", produces = { "application/hal+json" })
    public Resources<Task> getTasksForUser(@PathVariable final String userId) {
        final Collection<Task> tasks = taskService.getAllTasksForUser(userId);
        for (final Task task : tasks) {
            final Link selfLink = linkTo(
                methodOn(UserController.class).getTaskById(userId, task.getTaskId())).withSelfRel();
            task.add(selfLink);
        }

        Link link = linkTo(methodOn(UserController.class).getTasksForUser(userId)).withSelfRel();
        Resources<Task> result = new Resources<>(tasks, link);
        return result;
    }

    @RequestMapping(value = "/create/",method = RequestMethod.POST)
    public ResponseEntity<User> create(String userId, String username, String userSurname, String email) {
        User createdUser = userService.create(userId,username,userSurname,email);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .buildAndExpand(createdUser.getUserId())
                .toUri();

        return ResponseEntity.created(uri)
                .body(createdUser);

    }

    @RequestMapping(value="/update",method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User>  update(String username, String userSurname, String email, String userId) {
        User oldUser = userService.getUserDetail(userId);
        oldUser.setUserName(username);
        oldUser.setUserSurname(userSurname);
        oldUser.setMail(email);
        User updatedUser = userService.update(userId, oldUser);
        if (updatedUser == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(updatedUser);
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(String id) {
        userService.delete(id);
    }

    @GetMapping(produces = { "application/hal+json" })
    public Resources<User> getAllUsers() {
        final List<User> allUsers = userService.allUsers();

        for (final User user : allUsers) {
            String userId = user.getUserId();
            Link selfLink = linkTo(UserController.class).slash(userId).withSelfRel();
            user.add(selfLink);
            if (taskService.getAllTasksForUser(userId).size() > 0) {
                final Link tasksLink = linkTo(methodOn(UserController.class).getTasksForUser(userId)).withRel("allTasks");
                user.add(tasksLink);
            }
        }

        Link link = linkTo(UserController.class).withSelfRel();
        Resources<User> result = new Resources<>(allUsers, link);
        return result;
    }

}

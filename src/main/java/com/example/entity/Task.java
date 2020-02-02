package com.example.entity;

import org.springframework.hateoas.ResourceSupport;

import java.util.Date;

/**
 * Created by Diana Aimbetova on 2/2/2020
 **/

public class Task extends ResourceSupport {
    private String taskId;
    private String taskName;
    private String description;
    private Date creationDate;
    private Date deadLine;

    public Task() {
        super();
    }

    public Task(String taskId, String taskName, String description, Date creationDate, Date deadLine) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.description = description;
        this.creationDate = creationDate;
        this.deadLine = deadLine;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getDeadLine() {
        return deadLine;
    }

    public void setDeadLine(Date deadLine) {
        this.deadLine = deadLine;
    }
}

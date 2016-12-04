package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 12/2/2016.
 */

public class TaskR {
    private String taskName, taskStartDate, taskEndDate, createdDate, createdBy, assignTo ;
    private int taskID;
    private String toBeDoneBy, created;
    private int taskCompleted;

    public TaskR(String taskName, String taskStartDate, String taskEndDate, String createdDate, String createdBy, String assignTo, int taskID, String toBeDoneBy, String created, int taskCompleted) {
        this.taskName = taskName;
        this.taskStartDate = taskStartDate;
        this.taskEndDate = taskEndDate;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.assignTo = assignTo;
        this.taskID = taskID;
        this.toBeDoneBy = toBeDoneBy;
        this.created = created;
        this.taskCompleted = taskCompleted;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStartDate() {
        return taskStartDate;
    }

    public void setTaskStartDate(String taskStartDate) {
        this.taskStartDate = taskStartDate;
    }

    public String getTaskEndDate() {
        return taskEndDate;
    }

    public void setTaskEndDate(String taskEndDate) {
        this.taskEndDate = taskEndDate;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        if(createdBy=="null"){
            return "-";
        }else{
            return createdBy;
        }

    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getAssignTo() {
        if(assignTo=="null"){
            return "-";
        }else{
            return assignTo;
        }

    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getToBeDoneBy() {
            return toBeDoneBy;
    }

    public void setToBeDoneBy(String toBeDoneBy) {
        this.toBeDoneBy = toBeDoneBy;
    }

    public int getTaskCompleted() {
        return taskCompleted;
    }

    public void setTaskCompleted(int taskCompleted) {
        this.taskCompleted = taskCompleted;
    }
}

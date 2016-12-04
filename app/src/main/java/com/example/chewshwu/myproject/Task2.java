package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 10/6/2016.
 */

public class Task2 {

    private String taskName, taskStartDate, taskEndDate;
    private int taskID;
    private int checked;

    public Task2(String taskName, String taskStartDate, String taskEndDate, int taskID, int checked) {
        this.setTaskName(taskName);
        this.setTaskStartDate(taskStartDate);
        this.setTaskEndDate(taskEndDate);
        this.setTaskID(taskID);
        this.checked = checked;

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

    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public int getChecked(){
        return checked;
    }

    public void setChecked(int checked){
        this.checked = checked;
    }

  //  public void toggleChecked(){
  //      checked = !checked;
  //  }


}






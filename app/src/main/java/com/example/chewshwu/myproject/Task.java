package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 10/2/2016.
 */

public class Task {

        private String projectName;
        private int projectID;
        private String taskName, taskStartDate, taskEndDate;
        private int totalNoOfTask, noOfUncompleteTask;
        private int taskID;


        public Task(int projectID, String projectName, int totalNoOfTask, int noOfUncompleteTask ){
            this.setProjectID(projectID);
            this.setProjectName(projectName);
            this.setTotalNoOfTask(totalNoOfTask);
            this.setNoOfUncompleteTask(noOfUncompleteTask);


        }
  //      public Task(String taskName, String taskStartDate, String taskEndDate, int taskID) {
  //          this.setTaskName(taskName);
  //          this.setTaskStartDate(taskStartDate);
  //          this.setTaskEndDate(taskEndDate);
  //          this.setTaskID(taskID);
//
  //      }

        public String getProjectName(){
            return projectName;
        }

        public void setProjectName(String projectName){
            this.projectName = projectName;
        }

        public int getProjectID() {
            return projectID;
        }

        public void setProjectID(int projectID){
            this.projectID = projectID;
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


        public int getTotalNoOfTask() {
            return totalNoOfTask;
        }

        public void setTotalNoOfTask(int totalNoOfTask) {
            this.totalNoOfTask = totalNoOfTask;
        }

        public int getNoOfUncompleteTask(){
            return noOfUncompleteTask;
        }

        public void setNoOfUncompleteTask(int noOfUncompleteTask){
            this.noOfUncompleteTask = noOfUncompleteTask;
        }

        public int getTaskID() {
            return taskID;
        }

        public void setTaskID(int taskID) {
            this.taskID = taskID;
        }


}



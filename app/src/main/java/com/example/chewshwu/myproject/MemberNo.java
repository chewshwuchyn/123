package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 11/26/2016.
 */

public class MemberNo {

    private int projectID;
    private String projectName;
    private int numOfMembers;

    public MemberNo(int projectID, String projectName, int numOfMembers) {
        this.projectID = projectID;
        this.numOfMembers = numOfMembers;
        this.projectName = projectName;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(int numOfMembers) {
        this.numOfMembers = numOfMembers;
    }
}

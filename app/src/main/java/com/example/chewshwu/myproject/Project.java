package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 5/22/2016.
 */
public class Project {
    private String name, startDate, finishDate,type, createdOn;
    private int curMilestone, noOfMilestone, id;
    private double completion;

    public Project(String name, String startDate, String finishDate, int curMilestone, int noOfMilestone, String type, String createdOn, int id) {
        this.setName(name);
        this.setStartDate(startDate);
        this.setFinishDate(finishDate);
        this.setCurMilestone(curMilestone);
        this.setNoOfMilestone(noOfMilestone);
        this.setType(type);
        this.setCreatedOn(createdOn);
        this.setId(id);


    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public int getCurMilestone() {
        return curMilestone;
    }

    public void setCurMilestone(int curMilestone) {
        this.curMilestone = curMilestone;
    }

    public int getNoOfMilestone() {
        return noOfMilestone;
    }

    public void setNoOfMilestone(int noOfMilestone) {
        this.noOfMilestone = noOfMilestone;
    }

    public double getCompletion() {
        completion = Math.round(((double)getCurMilestone() / (double)getNoOfMilestone()) * 100);
        return completion;
    }

    public void setCompletion(double completion) {
        this.completion = completion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

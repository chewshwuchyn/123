package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 11/27/2016.
 */

public class ProjectMembers {

    private int user_id;
    private String name;
    private String email;
    private String position;
    private String imageurl;
    private int assignID;



    public ProjectMembers(int user_id, String name, String email, String position, String imageurl, int assignID) {
        this.user_id = user_id;
        this.name = name;
        this.email = email;
        this.position = position;
        this.imageurl = imageurl;
        this.assignID = assignID;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public int getAssignID() {
        return assignID;
    }

    public void setAssignID(int assignID) {
        this.assignID = assignID;
    }
}

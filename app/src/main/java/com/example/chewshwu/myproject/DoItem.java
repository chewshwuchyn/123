package com.example.chewshwu.myproject;

/**
 * Created by CHEW SHWU on 10/10/2016.
 */

public class DoItem {
    private String doName;
    private int doID, doComplete;
    private String reminderTime;
    private boolean checked;

    public DoItem(String doName,String reminderTime, int doID, int doComplete ){
        this.doName = doName;
        this.reminderTime = reminderTime;
        this.doID = doID;
        this.doComplete = doComplete;
        checked = false;
    }

    public String getDoName(){
        return doName;
    }

    public void setDoName(String doName){
        this.doName = doName;
    }

    public boolean isChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }

    public String getReminderTime(){
        return reminderTime;
    }

    public void setReminderTime(String reminderTime){
        this.reminderTime = reminderTime;
    }

    public int getDoID(){
        return doID;
    }

    public void setDoID(int doID){
        this.doID = doID;
    }


    public int getDoComplete(){return doComplete;}

    public void setDoComplete(int doComplete){
        this.doComplete = doComplete;
    }
}

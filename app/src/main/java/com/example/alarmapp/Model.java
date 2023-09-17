package com.example.alarmapp;

public class Model {
    private String alarmTime;
    private String alarmTitle;  // Add a field to store the alarm title
    private boolean isAlarmOn;  // Add a field to store the alarm state (on/off)

    public Model(String alarmTime) {
        this.alarmTime = alarmTime;
        this.isAlarmOn = false;  // Default alarm state is off

    }

    public String getAlarmTime() {
        return alarmTime;
    }

    public void setAlarmTime(String alarmTime) {
        this.alarmTime = alarmTime;
    }
    public String getAlarmTitle() {
        return alarmTitle;
    }

    public void setAlarmTitle(String alarmTitle) {
        this.alarmTitle = alarmTitle;
    }
    public boolean isAlarmOn() {
        return isAlarmOn;
    }

    public void setAlarmOn(boolean alarmOn) {
        isAlarmOn = alarmOn;
    }
}

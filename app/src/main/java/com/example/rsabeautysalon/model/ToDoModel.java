package com.example.rsabeautysalon.model;

public class ToDoModel  extends TaskId {

    private String task , due;
    private int status;
    private String location;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTask() {
        return task;
    }

    public String getDue() {
        return due;
    }

    public int getStatus() {
        return status;
    }
}


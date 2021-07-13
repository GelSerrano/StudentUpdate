package com.github.example.richtexteditor.Models;

public class Lesson {


    private String serializedtext;
    private String week;
    private String title;

    public Lesson(){
        //empty constructor
    }

    public String getSerializedtext() {
        return serializedtext;
    }

    public void setSerializedtext(String serializedtext) {
        this.serializedtext = serializedtext;
    }

    public String getWeek() {     return week;  }

    public void setWeek(String week) {     this.week = week;    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

package com.example.jacobwilliams.todoapp;

import android.media.Image;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.UUID;

/**
 * Created by jacobwilliams on 10/25/16.
 */

public class toDoItem implements Comparable<toDoItem>{
    @SerializedName("title")
    private String title;

    @SerializedName("text")
    private String text;

    @SerializedName("month")
    private String month;

    @SerializedName("day")
    private String day;

    @SerializedName("time")
    private String time;

    @SerializedName("date")
    private Date date;

    @SerializedName("category")
    private String category;

    @SerializedName("image")
    private Image image;

    public toDoItem(String time, String day, String month, String text, Date date, String title,String category) {
        this.time = time;
        this.day = day;
        this.month = month;
        this.text = text;
        this.date = date;
        this.title = title;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public int compareTo(toDoItem another) {
        return getCategory().compareTo(another.getCategory());
    }
}

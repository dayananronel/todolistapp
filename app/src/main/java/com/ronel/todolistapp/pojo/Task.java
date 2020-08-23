package com.ronel.todolistapp.pojo;

import com.google.gson.annotations.SerializedName;

public class Task  implements Comparable<Task>{
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("date")
    String date;
    @SerializedName("completed")
    boolean completed;

    public Task(int id, String title, String date, boolean completed) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.completed = completed;
    }
    public Task(String title, String date, boolean completed) {
        this.title = title;
        this.date = date;
        this.completed = completed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public int compareTo(Task task) {
        return 1;
    }
}

package com.example.mobile_contentsapp.Commu.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User;

public class Commu_Comment_List_Get {
    private int id;
    private String contents;
    private String createdDate;
    private User user;

    public Commu_Comment_List_Get(int id, String contents, String createdDate, User user) {
        this.id = id;
        this.contents = contents;
        this.createdDate = createdDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


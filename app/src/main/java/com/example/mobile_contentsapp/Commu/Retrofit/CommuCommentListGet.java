package com.example.mobile_contentsapp.Commu.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User;

import java.util.Date;

public class CommuCommentListGet {
    private int id;
    private String contents;
    private Date createdDate;
    private User user;

    public CommuCommentListGet(int id, String contents, Date createdDate, User user) {
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

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}


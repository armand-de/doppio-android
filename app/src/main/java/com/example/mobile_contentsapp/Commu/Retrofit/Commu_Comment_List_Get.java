package com.example.mobile_contentsapp.Commu.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User_Data_List;

public class Commu_Comment_List_Get {
    private int id;
    private String contents;
    private String createdDate;
    private User_Data_List user;

    public Commu_Comment_List_Get(int id, String contents, String createdDate, User_Data_List user) {
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

    public User_Data_List getUser() {
        return user;
    }

    public void setUser(User_Data_List user) {
        this.user = user;
    }
}


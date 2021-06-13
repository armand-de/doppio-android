package com.example.mobile_contentsapp.Commu.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User_Data_List;

public class Commu_Find_Get {
    private int id;
    private String title;
    private String image;
    private String contents;
    private String createdDate;
    private int preference;
    private User_Data_List user;

    public Commu_Find_Get(int id, String title, String image, String contents, String createdDate, int preference, User_Data_List user) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.contents = contents;
        this.createdDate = createdDate;
        this.preference = preference;
        this.user = user;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

    public int getPreference() {
        return preference;
    }

    public void setPreference(int preference) {
        this.preference = preference;
    }

    public User_Data_List getUser() {
        return user;
    }

    public void setUser(User_Data_List user) {
        this.user = user;
    }
}


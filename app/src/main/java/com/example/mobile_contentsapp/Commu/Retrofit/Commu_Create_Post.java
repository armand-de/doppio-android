package com.example.mobile_contentsapp.Commu.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User_Data_List;

import java.util.Date;

public class Commu_Create_Post {
    private String title;
    private String image;
    private String contents;

    public Commu_Create_Post(String title, String image, String contents) {
        this.title = title;
        this.image = image;
        this.contents = contents;
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
}

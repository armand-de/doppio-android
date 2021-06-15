package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User_Data_List;

public class ProfileGet {
    private String nickname;
    private String image;
    private User_Data_List user;

    public ProfileGet(String nickname, String image, User_Data_List user) {
        this.nickname = nickname;
        this.image = image;
        this.user = user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public User_Data_List getUser() {
        return user;
    }

    public void setUser(User_Data_List user) {
        this.user = user;
    }
}

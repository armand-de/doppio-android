package com.example.mobile_contentsapp.Recipe.Retrofit;

public class User_Data_List {
    private String id;
    private String nickname;
    private String image;

    public User_Data_List(String id, String nickname, String image) {
        this.id = id;
        this.nickname = nickname;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
}

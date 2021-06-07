package com.example.mobile_contentsapp.Login.Retrofit;

public class Authorization {
    private String accessToken;

    public Authorization(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}

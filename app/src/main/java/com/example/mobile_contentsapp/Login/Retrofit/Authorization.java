package com.example.mobile_contentsapp.Login.Retrofit;

public class Token {
    
    private String AccessToken;

    public Token(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }
}

package com.example.mobile_contentsapp.Login.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TokenCheck_Client {

    public static TokenCheck_Api getApiService(){
        return getInstance().create(TokenCheck_Api.class);
    }
    public static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(NumberClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

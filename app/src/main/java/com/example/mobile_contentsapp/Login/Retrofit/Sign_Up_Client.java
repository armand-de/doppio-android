package com.example.mobile_contentsapp.Login.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_Up_Client {

    public static Sign_Up_Api getApiService(){
        return getInstance().create(Sign_Up_Api.class);
    }
    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(NumberClient.BASE_RUL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

package com.example.mobile_contentsapp.Login.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NumberClient {
    public static final String BASE_RUL = "https://server.khjcode.repl.co/";

    public static Nnmber_Api getApiService(){
        return getInstance().create(Nnmber_Api.class);
    }
    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_RUL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

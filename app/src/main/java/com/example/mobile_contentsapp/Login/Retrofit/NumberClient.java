package com.example.mobile_contentsapp.Login.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NumberClient {
    public static final String BASE_URL = "http://10.0.2.2:5050/";

    public static Nnmber_Api getApiService(){
        return getInstance().create(Nnmber_Api.class);
    }
    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

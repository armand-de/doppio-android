package com.example.mobile_contentsapp.Login.Retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Sign_In_Client {




    public static Sign_in_Api getApiService(){
        return getInstance().create(Sign_in_Api.class);
    }
    public static Retrofit getInstance(){


        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(NumberClient.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

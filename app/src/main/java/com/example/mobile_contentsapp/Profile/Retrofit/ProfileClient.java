package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.mobile_contentsapp.Login.Retrofit.NumberClient.BASE_URL;

public class MyCommuListClient {

    public static MyCommuListApi getApiService(){
        return getInstance().create(MyCommuListApi.class);
    }
    private static Retrofit getInstance(){
        Gson gson = new GsonBuilder().setLenient().create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }
}

package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface FindOtherProfileApi {
    @GET("/user/{id}")
    Call<User> findProfile(@Header("Authorization") String token, @Path("id") String id);
}

package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.PUT;

public interface ProfileUpdateApi {
    @PUT("/user")
    Call<User> profileUpdateCall (@Header("Authorization") String token, @Body ProfileUpdate profileUpdata);

}

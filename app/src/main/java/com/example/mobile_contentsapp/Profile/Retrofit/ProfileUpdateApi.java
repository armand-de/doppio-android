package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ProfileUpdateApi {
    @POST("/user/update")
    Call<User> profileUpdateCall (@Header("Authorization") String token, @Body ProfileUpdata profileUpdata);

}

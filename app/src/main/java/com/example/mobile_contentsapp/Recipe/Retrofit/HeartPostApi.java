package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HeartPostApi {
    @POST("/recipe/create/preference")
    Call<HeartPostId> heartPostcall(@Header("Authorization") String token, @Body HeartPostId heartPostId);
}

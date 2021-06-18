package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface HeartFindApi {
    @POST("/recipe/my/preference/is-exist")
    Call<HeartFindPost> heartFindCall(@Header("Authorization") String token, @Body HeartPostId heartPostId);
}

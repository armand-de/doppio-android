package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Heart_Api {
    @POST("/recipe/my/preference/is-exist")
    Call<Heart_Post> heartGetCall(@Header("Authorization") String token, @Body Heart_Post_Id heart_post_id);
}

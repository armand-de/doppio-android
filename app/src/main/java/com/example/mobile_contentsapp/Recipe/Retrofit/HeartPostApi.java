package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Heart_Recommand_Api {
    @POST("/recipe/create/preference")
    Call<Heart_Post_Id> recommandCall(@Header("Authorization") String token, @Body Heart_Post_Id heart_post_id);
}

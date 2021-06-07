package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Recipe_Api {
    @POST("/recipe/create")
    Call<Recipe_Post> recipe_post_call(@Header("Authorization") String token, @Body Recipe_Post recipe_post);

}

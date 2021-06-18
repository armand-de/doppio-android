package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RecipeApi {
    @POST("/recipe/create")
    Call<RecipePost> recipe_post_call(@Header("Authorization") String token, @Body RecipePost recipe_post);

}

package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface RecipeCreateApi {
    @POST("/recipe/create")
    Call<RecipeCreatePost> recipeCreateCall(@Header("Authorization") String token, @Body RecipeCreatePost recipeCreatePost);

}

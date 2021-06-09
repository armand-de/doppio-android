package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Recipe_Find_Api {
    @GET("/recipe/find/id/{id}/user")
    Call<Recipe_Find_Get> recipe_find_call(@Path("id") String id);
}

package com.example.mobile_contentsapp.Recipe.Retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface RecipeSearchApi {
    @GET("/recipe")
    Call<List<RecipeListGet>> recipeSearchCall(@Query("start") String start,
                                               @Query("keyword") String keyword,
                                               @Query("category") int category);
}

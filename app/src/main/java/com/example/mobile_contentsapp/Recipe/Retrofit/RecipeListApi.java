package com.example.mobile_contentsapp.Recipe.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeListApi {
    @GET("/recipe/list?")
    Call<List<RecipeListGet>> recipeListCall(@Query("start") int start);
}

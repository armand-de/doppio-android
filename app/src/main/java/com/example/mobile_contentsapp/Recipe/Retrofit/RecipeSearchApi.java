package com.example.mobile_contentsapp.Recipe.Retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RecipeSearchApi {
    @GET("/recipe/list/search")
    Call<List<RecipeListGet>> recipeSearchCall(@QueryMap Map<String, String> option);
}

package com.example.mobile_contentsapp.Recipe.Retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Recipe_Search_Api {
    @GET("/recipe/list/search")
    Call<List<RecipeListGet>> recipe_SearchGetCall(@QueryMap Map<String, String> option);
}

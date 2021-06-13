package com.example.mobile_contentsapp.Recipe.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Recipe_List_Api {
    @GET("/recipe/list?")
    Call<List<Recipe_List_Get>> recipe_list_get_call(@Query("start") int step);
}

package com.example.mobile_contentsapp.Recipe.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Recipe_List_Api {
    @GET("/recipe/list/{step}/preference")
    Call<List<Recipe_List_Get>> recipe_list_get_call(@Path("step")int step);
}

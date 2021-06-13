package com.example.mobile_contentsapp.Recipe.Retrofit;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface Recipe_Search_Api {
    @GET("/recipe/list/search")
    Call<List<Recipe_List_Get>> recipe_SearchGetCall(@QueryMap Map<String, String> option);
}

package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeListGet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface OtherRecipeListApi {
    @GET("/recipe/my")
    Call<List<RecipeListGet>> myRecipeListCall (@Header("Authorization") String token);
}

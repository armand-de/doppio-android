package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Recipe.Retrofit.RecipeListGet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyRecipeListApi {
    @GET("/recipe/user-id/{userid}")
    Call<List<RecipeListGet>> myRecipeListCall (@Path("userid") String id);
}

package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RecipeFindApi {
    @GET("/recipe/find/id/{id}/user")
    Call<RecipeFindGet> recipeFindCall(@Path("id") int id);
}

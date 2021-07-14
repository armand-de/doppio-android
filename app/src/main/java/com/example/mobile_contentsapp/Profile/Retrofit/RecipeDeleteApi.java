package com.example.mobile_contentsapp.Profile.Retrofit;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface RecipeDeleteApi {
    @DELETE("/recipe/{id}")
    Call<Void> deleterecipe(@Header("Authorization") String token, @Path("id") int id);
}

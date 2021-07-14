package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HeartFindApi {
    @GET("/recipe/preference/{id}")
    Call<HeartFindPost> heartFindCall(@Header("Authorization") String token, @Path("id") int id);
}

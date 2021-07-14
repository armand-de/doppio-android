package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HeartCancelApi {
    @DELETE("/recipe/preference/{id}")
    Call<HeartPostId> cancelCall(@Header("Authorization") String token, @Path("id") int id);
}

package com.example.mobile_contentsapp.Profile.Retrofit;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CommuDeleteApi {
    @DELETE("/post/{id}")
    Call<Void> deletecommu(@Header("Authorization") String token, @Path("id") int id);
}

package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface TokenCheckApi {
    @GET("/auth")
    Call<Void> tokenCehckCall(@Header("Authorization") String token);
}

package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInApi {
    @POST("auth/login")
    Call<Authorization> signInCall(@Body SignInPost signInPost);
}

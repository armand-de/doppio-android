package com.example.mobile_contentsapp.Login.Retrofit;

import android.app.DownloadManager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Sign_in_Api {
    @POST("auth/login")
    Call<Token> sign_in_post_call(@Body Sign_in_Post login_post);
}

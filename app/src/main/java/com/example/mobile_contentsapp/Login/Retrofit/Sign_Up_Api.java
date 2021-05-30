package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Sign_Up_Api {
    @POST("auth/verify")
    Call<Sign_Up_Post> sign_up_postCall(@Body Sign_Up_Post sign_up_post);
}

package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Sign_Up_Api {
    @POST("auth/verify")
    Call<SignUpPost> sign_up_postCall(@Body SignUpPost sign_up_post);
}

package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignUpApi {
    @POST("auth/verify")
    Call<SignUpPost> signUpCall(@Body SignUpPost signUpPost);
}

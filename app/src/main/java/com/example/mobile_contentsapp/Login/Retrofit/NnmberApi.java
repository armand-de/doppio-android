package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface Nnmber_Api {

    @POST("auth/join")
    Call<Number_Post> NumberpostCall(@Body Number_Post Number_Post);
}

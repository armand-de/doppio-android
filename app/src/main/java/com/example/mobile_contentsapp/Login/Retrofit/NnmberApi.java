package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NnmberApi {

    @POST("auth/join")
    Call<NumberPost> NumberCall(@Body NumberPost NumberPost);
}

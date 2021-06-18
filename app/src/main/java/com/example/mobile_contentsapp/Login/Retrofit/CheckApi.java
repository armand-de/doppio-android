package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CheckApi {

    @GET("/user/exist/nickname/{nickname}")
    Call<CheckPost> checkCall(@Path("nickname") String nickname);
}

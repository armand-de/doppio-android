package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Check_Api {
    String nickname = null;

    @GET("/user/exist/nickname/{nickname}")
    Call<Check_Post> check_postcall(@Path("nickname") String nickname);
}

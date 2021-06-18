package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface MyCommuListApi {
    @GET("/post/my")
    Call<List<CommuListGet>> myCommuListCall(@Header("Authorization") String token);

}

package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommuSearchApi {
    @GET("/post")
    Call<List<CommuListGet>> commuSearchCall(@Query("start") int start, @Query("keyword") String keyword);
}

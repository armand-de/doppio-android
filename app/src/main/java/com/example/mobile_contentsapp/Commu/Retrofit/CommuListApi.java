package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CommuListApi {
    @GET("/post/list")
    Call<List<CommuListGet>> commuListApiCall(@Query("start") int start);

}

package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Commu_List_Api {
    @GET("/post/list")
    Call<List<Commu_List_Get>> commuListApiCall(@Query("start") int start);

}

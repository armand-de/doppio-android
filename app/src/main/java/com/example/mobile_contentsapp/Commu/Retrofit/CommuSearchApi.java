package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Commu_Search_Api {
    @GET("/post/list/search/{keyword}")
    Call<List<Commu_List_Get>> commuSearchCall(@Path("keyword") String keyword, @Query("start") int start);
}

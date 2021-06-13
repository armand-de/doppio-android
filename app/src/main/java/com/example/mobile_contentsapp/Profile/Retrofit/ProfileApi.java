package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Get;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MyCommuListApi {
    @GET("/post/my")
    Call<List<Commu_List_Get>> myCommuListCall(@Header("Authorization") String token);

}

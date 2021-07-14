package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Commu.Retrofit.CommuListGet;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface MyCommuListApi {
    @GET("/post/user-id/{userid}")
    Call<List<CommuListGet>> myCommuListCall(@Path("userid") String id);

}

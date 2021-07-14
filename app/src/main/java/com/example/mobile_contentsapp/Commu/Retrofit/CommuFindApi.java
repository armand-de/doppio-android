package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommuFindApi {
    @GET("/post/{id}")
    Call<CommuFindGet> commuFindApiCall(@Path("id") int id);

}

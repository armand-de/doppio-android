package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CommuHeartSelectApi {
    @GET("/post/preference/{id}")
    Call<CommuHeartSelectGet> commuHeartSelectApiCall(@Header("Authorization") String token, @Path("id") int id);

}

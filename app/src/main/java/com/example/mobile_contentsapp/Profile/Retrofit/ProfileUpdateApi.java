package com.example.mobile_contentsapp.Profile.Retrofit;

import com.example.mobile_contentsapp.Commu.Retrofit.Commu_List_Get;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ProfileApi {
    @GET("/auth/profile")
    Call<ProfileGet> profileCall (@Header("Authorization") String token);

}

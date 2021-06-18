package com.example.mobile_contentsapp.Profile.Retrofit;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Header;

public interface DeleteApi {
    @DELETE("/user/delete")
    Call<Void> deleteAccount(@Header("Authorization") String token);
}

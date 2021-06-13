package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Commu_Heart_Api {
    @POST("/post/create/preference")
    Call<Commu_Heart> commuHeartApiCall (@Header ("Authorization") String token,
                                                           @Body Commu_Heart commu_heart);


}

package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Commu_Heart_Down_Api {
    @POST("/post/delete/preference")
    Call<Commu_Heart_Up> commuHeartDownApiCall(@Header ("Authorization") String token,
                                               @Body Commu_Heart_Up commu_heart);


}

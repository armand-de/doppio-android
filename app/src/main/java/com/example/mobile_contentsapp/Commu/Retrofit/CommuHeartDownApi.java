package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CommuHeartDownApi {
    @POST("/post/delete/preference")
    Call<CommuHeartUp> commuHeartDownApiCall(@Header ("Authorization") String token,
                                             @Body CommuHeartUp commu_heart);


}

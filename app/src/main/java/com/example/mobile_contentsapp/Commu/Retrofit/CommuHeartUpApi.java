package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommuHeartUpApi {
    @POST("/post/preference/")
    Call<CommuHeartUp> commuHeartUpApiCall(@Header ("Authorization") String token,
                                           @Body CommuHeartUp commu_heart);


}

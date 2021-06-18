package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CommuCreateApi {
    @POST("/post/create")
    Call<CommuCreatePost> commuCreateApi(@Header ("Authorization") String token , @Body CommuCreatePost commu_create_post);

}

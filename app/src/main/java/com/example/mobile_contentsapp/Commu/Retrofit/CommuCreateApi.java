package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface Commu_Create_Api {
    @POST("/post/create")
    Call<Commu_Create_Post> commuCreateApi(@Header ("Authorization") String token , @Body Commu_Create_Post commu_create_post);

}

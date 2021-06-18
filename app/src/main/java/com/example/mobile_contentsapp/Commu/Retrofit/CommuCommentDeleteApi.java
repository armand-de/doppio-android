package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Commu_Heart_Select_Api {
    @GET("/post/is-exist/preference/{postId}")
    Call<Commu_Heart_Select_Get> commuHeartSelectApiCall( @Path("postId") int id);

}

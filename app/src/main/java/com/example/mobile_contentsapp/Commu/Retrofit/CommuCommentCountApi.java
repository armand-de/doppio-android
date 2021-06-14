package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Commu_Comment_Count_Api {
    @GET("/comment/count/list/{}")
    Call<Void> commuFindApiCall( @Path("postId") int count);

}

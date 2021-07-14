package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CommuCommentCountApi {
    @GET("/comment/count/{id}")
    Call<CommuCommentCountGet> commuCommentCountCall(@Path("id") int id);

}

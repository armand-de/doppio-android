package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CommuCommentListApi {
    @GET("/comment/list/{postId}")
    Call<List<CommuCommentListGet>> commuCommentListApiCall(@Header ("Authorization") String token, @Path("postId") int id, @Query("start") int start );
}

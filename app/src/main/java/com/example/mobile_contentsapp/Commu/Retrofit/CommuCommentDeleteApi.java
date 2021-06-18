package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CommuCommentDeleteApi {
    @DELETE("/comment/delete/{postId}")
    Call<Void> commuHeartSelectCall(@Header("Authorization") String token, @Path("postId") int id);

}

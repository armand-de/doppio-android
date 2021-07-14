package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface CommuCommentDeleteApi {
    @DELETE("/comment/{id}")
    Call<Void> commuHeartSelectCall(@Header("Authorization") String token , @Path("id") int id);

}

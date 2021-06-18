package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Commu_Comment_Post_Api {
    @POST("/comment/create")
    Call<Commu_Comment_Post> commuCommentPostApiCall(@Header ("Authorization") String token,
                                                           @Body Commu_Comment_Post commu_comment_post);

}

package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface CommuCommentPostApi {
    @POST("/comment")
    Call<CommuCommentPost> commuCommentPostCall(@Header ("Authorization") String token,
                                                @Body CommuCommentPost commu_comment_post);
}

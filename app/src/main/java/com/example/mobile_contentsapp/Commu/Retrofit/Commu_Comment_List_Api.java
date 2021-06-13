package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface Commu_Comment_List_Api {
    @GET("/post/find/id/{id}")
    Call<List<Commu_Comment_List_Get>> commuCommentListApiCall(@Path("id") int id);

}

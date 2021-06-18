package com.example.mobile_contentsapp.Commu.Retrofit;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Commu_Comment_List_Api {
    @GET("/comment/list/{postId}")
    Call<List<Commu_Comment_List_Get>> commuCommentListApiCall(@Header ("Authorization") String token, @Path("postId") int id, @Query("start") int start );
}

package com.example.mobile_contentsapp.Commu.Retrofit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Commu_Find_Api {
    @GET("/post/find/id/{id}")
    Call<Commu_Find_Get> commuFindApiCall( @Path("id") int id);

}

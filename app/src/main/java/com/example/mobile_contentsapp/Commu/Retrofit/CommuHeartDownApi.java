package com.example.mobile_contentsapp.Commu.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CommuHeartDownApi {
    @DELETE("/post/preference/{id}")
    Call<CommuHeartUp> commuHeartDownApiCall(@Header ("Authorization") String token,
                                             @Path("id") int id);


}

package com.example.mobile_contentsapp.Recipe.Retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Page_Api {
    @GET("/recipe/list/count/page")
    Call<Page_Get> page_amount_get_call();


}

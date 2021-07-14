package com.example.mobile_contentsapp.Login.Retrofit;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface ChangePasswordApi {
    @PUT("/auth/password")
    Call<ChangePasswordPut> changePassword (@Body ChangePasswordPut changePasswordPut);

}

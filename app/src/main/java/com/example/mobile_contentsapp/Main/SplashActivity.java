package com.example.mobile_contentsapp.Main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_contentsapp.Login.Retrofit.TokenCheckClient;
import com.example.mobile_contentsapp.Login.SignInActivity;
import com.example.mobile_contentsapp.Profile.Retrofit.ProfileClient;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Recipe.Retrofit.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SplashActivity extends AppCompatActivity {

    public static String tokenValue;
    public static String userId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedPreferences = getSharedPreferences("sp",MODE_PRIVATE);
        tokenValue = sharedPreferences.getString("token","");

        tokenCheck(tokenValue);


    }
    public void tokenCheck(String token){
        Call<Void> call = TokenCheckClient.getApiService().tokenCehckCall(token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (!response.isSuccessful()){
                    Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                    startActivity(intent);
                    return;
                }
                getUserId();
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Intent intent = new Intent(SplashActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getUserId(){
        Call<User> call = ProfileClient.getApiService().profileCall(tokenValue);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                userId = response.body().getId();
                Log.d(TAG, "onResponse: "+userId);
                
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}

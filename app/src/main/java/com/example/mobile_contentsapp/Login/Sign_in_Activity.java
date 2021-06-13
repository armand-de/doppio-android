package com.example.mobile_contentsapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobile_contentsapp.Login.Retrofit.Sign_In_Client;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_in_Post;
import com.example.mobile_contentsapp.Login.Retrofit.Authorization;
import com.example.mobile_contentsapp.Login.Retrofit.TokenCheck_Client;
import com.example.mobile_contentsapp.Login.Retrofit.TokenCheck_Get;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Main.Main_Activity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Sign_in_Activity extends AppCompatActivity {

    public EditText nicknameedit;
    public EditText passwordedit;
    public static String tokenValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        SharedPreferences sharedPreferences = getSharedPreferences("sp",MODE_PRIVATE);
        tokenValue = sharedPreferences.getString("token","");

        tokenCheck(tokenValue);

        nicknameedit = findViewById(R.id.nickname_edit);
        passwordedit = findViewById(R.id.password_edit);

        Button signin = findViewById(R.id.singinbtn);
        Button signup = findViewById(R.id.signupbtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), Signup_Activity.class);
            startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin(nicknameedit.getText().toString(),passwordedit.getText().toString());
            }
        });
    }
    public void tokenCheck(String token){
        Call<TokenCheck_Get> call = TokenCheck_Client.getApiService().tokenCehckCall(token);
        call.enqueue(new Callback<TokenCheck_Get>() {
            @Override
            public void onResponse(Call<TokenCheck_Get> call, Response<TokenCheck_Get> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");

                Intent intent = new Intent(Sign_in_Activity.this, Main_Activity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<TokenCheck_Get> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러"+t.getMessage());
            }
        });
    }

    public void signin(String nick, String pass){

        Sign_in_Post sign_in_post = new Sign_in_Post(nick,pass);
        Call<Authorization> call = Sign_In_Client.getApiService().sign_in_post_call(sign_in_post);

        call.enqueue(new Callback<Authorization>() {
            @Override
            public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    Toast.makeText(Sign_in_Activity.this, "닉네임또는 비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onResponse: 성공"+response.code());
                Intent intent = new Intent(getApplicationContext(), Main_Activity.class);
                startActivity(intent);
                Authorization token = response.body();
                SharedPreferences sharedPreferences =  getSharedPreferences("sp",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String tokenValue = token.getAccessToken();
                Log.d(TAG, "토큰값"+ tokenValue);
                editor.putString("token",tokenValue);
                editor.commit();
            }

            @Override
            public void onFailure(Call<Authorization> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 오류 "+t.getMessage());
            }
        });

    }

}
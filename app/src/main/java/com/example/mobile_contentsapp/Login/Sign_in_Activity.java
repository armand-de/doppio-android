package com.example.mobile_contentsapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobile_contentsapp.Login.Retrofit.Sign_In_Client;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_Up_Client;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_Up_Post;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_in_Api;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_in_Post;
import com.example.mobile_contentsapp.Login.Retrofit.Token;
import com.example.mobile_contentsapp.Main_Activity;
import com.example.mobile_contentsapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Sign_in_Activity extends AppCompatActivity {

    EditText nicknameedit;
    EditText passwordedit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

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
    public void signin(String nick, String pass){

        Sign_in_Post sign_in_post = new Sign_in_Post(nick,pass);
        Call<Token> call = Sign_In_Client.getApiService().sign_in_post_call(sign_in_post);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공"+response.code());
                Intent intent = new Intent(getApplicationContext(),Main_Activity.class);
                startActivity(intent);
                Token token = response.body();
                SharedPreferences.Editor editor;
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 오류 "+t.getMessage());
            }
        });

    }

}
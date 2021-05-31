package com.example.mobile_contentsapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mobile_contentsapp.Login.Retrofit.CheckClient;
import com.example.mobile_contentsapp.Login.Retrofit.Check_Api;
import com.example.mobile_contentsapp.Login.Retrofit.Check_Post;
import com.example.mobile_contentsapp.Login.Retrofit.NumberClient;
import com.example.mobile_contentsapp.Login.Retrofit.Number_Post;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_Up_Client;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_Up_Post;
import com.example.mobile_contentsapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

public class Signup_Activity extends AppCompatActivity {

    private boolean exist = false;
    
    private EditText nicknameedit;
    private EditText passwordedit;
    private EditText phoneedit;
    private EditText verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        nicknameedit = findViewById(R.id.nickname_edit);
        passwordedit = findViewById(R.id.password_edit);
        phoneedit = findViewById(R.id.phone_edit);
        verify = findViewById(R.id.number_edit);
        Button phonebtn = findViewById(R.id.number_btn);
        Button signupbtn = findViewById(R.id.sign_up_btn);
        Button checkbtn = findViewById(R.id.check_nick_btn);

        nicknameedit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (exist == true){
                    checkbtn.setText("중복 확인");
                    checkbtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_line_btn));
                    checkbtn.setTextColor(Color.parseColor("#2D665F"));
                    exist = false;
                }
            }
        });

        phonebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber(phoneedit.getText().toString());
            }
        });
        checkbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exist == false){ 
                    check(nicknameedit.getText().toString(),checkbtn);
                }
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp(nicknameedit.getText().toString(),passwordedit.getText().toString(),phoneedit.getText().toString(),verify.getText().toString());
            }
        });

    }
    public void phoneNumber(String phone){
        Number_Post number_post = new Number_Post(phone);
        Call<Number_Post> call = NumberClient.getApiService().NumberpostCall(number_post);
        call.enqueue(new Callback<Number_Post>() {
            @Override
            public void onResponse(Call<Number_Post> call, Response<Number_Post> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공"+response.body().toString());
            }

            @Override
            public void onFailure(Call<Number_Post> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
                return;
            }
        });
    }
    public void signUp(String nick , String pass, String phone, String verify){
        Sign_Up_Post sign_up_post = new Sign_Up_Post(nick,pass,phone,verify);
        Call<Sign_Up_Post> call = Sign_Up_Client.getApiService().sign_up_postCall(sign_up_post);
        call.enqueue(new Callback<Sign_Up_Post>() {
            @Override
            public void onResponse(Call<Sign_Up_Post> call, Response<Sign_Up_Post> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 망함"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
            }

            @Override
            public void onFailure(Call<Sign_Up_Post> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
            }
        });
    }

    public void check(String nick, Button btn){
        Call<Check_Post> call = CheckClient.getApiService().check_postcall(nick);
        call.enqueue(new Callback<Check_Post>() {
            @Override
            public void onResponse(Call<Check_Post> call, Response<Check_Post> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                Check_Post result = response.body();
                Log.d(TAG, "onResponse: "+result.isExist());
                if (result.isExist() == false){
                    exist = true;
                    btn.setText("확인 완료");
                    btn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_btn));
                    btn.setTextColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void onFailure(Call<Check_Post> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
            }
        });
    }
}
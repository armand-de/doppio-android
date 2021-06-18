package com.example.mobile_contentsapp.Login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile_contentsapp.Login.Retrofit.CheckClient;
import com.example.mobile_contentsapp.Login.Retrofit.Check_Post;
import com.example.mobile_contentsapp.Login.Retrofit.NumberClient;
import com.example.mobile_contentsapp.Login.Retrofit.Number_Post;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_Up_Client;
import com.example.mobile_contentsapp.Login.Retrofit.Sign_Up_Post;
import com.example.mobile_contentsapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class Signup_Activity extends AppCompatActivity {

    private boolean exist = false;
    
    private EditText nicknameEdit;
    private EditText passwordEdit;
    private EditText passConfirmEdit;
    private EditText phoneEdit;
    private EditText verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        nicknameEdit = findViewById(R.id.nickname_edit);
        passwordEdit = findViewById(R.id.password_edit);
        passConfirmEdit = findViewById(R.id.password_confirm_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        verify = findViewById(R.id.number_edit);

        Button phoneBtn = findViewById(R.id.number_btn);
        Button signUpBtn = findViewById(R.id.sign_up_btn);
        Button signInbtn = findViewById(R.id.sign_in_intent_btn);
        Button checkBtn = findViewById(R.id.check_nick_btn);

        TextView nickLimit = findViewById(R.id.nick_limit);
        TextView passLimit = findViewById(R.id.pass_limit);
        nickLimit.setVisibility(View.INVISIBLE);
        passLimit.setVisibility(View.INVISIBLE);

        nicknameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(nicknameEdit.getText().toString().length() >= 3) || !(nicknameEdit.getText().toString().length() <= 8)){
                    nickLimit.setVisibility(View.VISIBLE);
                }else{
                    nickLimit.setVisibility(View.INVISIBLE);
                }
                if (exist == true){
                    checkBtn.setText("중복 확인");
                    checkBtn.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.round_line_btn));
                    checkBtn.setTextColor(Color.parseColor("#2D665F"));
                    exist = false;
                }
            }
        });

        passwordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!(passwordEdit.getText().toString().length() >= 7) || !(passwordEdit.getText().toString().length() <= 30)){
                    passLimit.setVisibility(View.VISIBLE);
                }else{
                    passLimit.setVisibility(View.INVISIBLE);
                }
            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber(phoneEdit.getText().toString());
            }
        });
        checkBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exist == false){ 
                    check(nicknameEdit.getText().toString(),checkBtn);
                }
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (nicknameEdit.getText().toString().length() >= 3 && nicknameEdit.getText().toString().length() <= 8){
                    if (passwordEdit.getText().toString().length() >= 7 && passwordEdit.getText().toString().length() <= 30){
                        if (passConfirmEdit.getText().toString().equals(passwordEdit.getText().toString())){
                            signUp(nicknameEdit.getText().toString(), passwordEdit.getText().toString(), phoneEdit.getText().toString(),verify.getText().toString());
                        }else{
                            faii();
                        }
                    }else {
                        faii();
                    }
                }else{
                    faii();
                }
            }
        });
        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signup_Activity.this,Sign_in_Activity.class);
                finish();
                startActivity(intent);
            }
        });

    }
    public void faii(){
        Toast.makeText(this, "정확한 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
    }

    public void phoneNumber(String phone){
        Number_Post number_post = new Number_Post(phone);
        Call<Number_Post> call = NumberClient.getApiService().NumberpostCall(number_post);
        call.enqueue(new Callback<Number_Post>() {
            @Override
            public void onResponse(Call<Number_Post> call, Response<Number_Post> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    Toast.makeText(Signup_Activity.this, "유효한 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Signup_Activity.this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                finish();
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
package com.example.mobile_contentsapp.Login;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_contentsapp.Login.Retrofit.ChangePasswordClient;
import com.example.mobile_contentsapp.Login.Retrofit.ChangePasswordPut;
import com.example.mobile_contentsapp.Login.Retrofit.NumberClient;
import com.example.mobile_contentsapp.Login.Retrofit.NumberPost;
import com.example.mobile_contentsapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FindPasswordActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password2);

        getWindow().setStatusBarColor(Color.parseColor("#f1f2f3"));

        Intent intent = getIntent();
        String phone = intent.getStringExtra("phone");

        TextView verify = findViewById(R.id.verify_repost);
        EditText numberEdit = findViewById(R.id.number_edit);
        EditText passwordEdit = findViewById(R.id.password_edit);
        EditText passwordconfirmEdit = findViewById(R.id.password_confirm_edit);

        ImageButton backBtn = findViewById(R.id.find_pass_back1);
        Button changeBtn = findViewById(R.id.change_btn);

        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (passwordEdit.getText().toString().length()>=7&&
                        passwordEdit.getText().toString().length()<=20&&
                passwordconfirmEdit.getText().toString().equals(passwordEdit.getText().toString())){
                    changePassword(phone,numberEdit.getText().toString(),passwordEdit.getText().toString());
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(FindPasswordActivity2.this)
                .setMessage("인증번호를 재전송하시겠습니까?")
                        .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                phoneNumber(phone);
                            }
                        });
                Dialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    private void changePassword(String phone, String number, String password){
        ChangePasswordPut changePasswordPut = new ChangePasswordPut(phone,number,password);
        Call<ChangePasswordPut> call = ChangePasswordClient.getApiService().changePassword(changePasswordPut);
        call.enqueue(new Callback<ChangePasswordPut>() {
            @Override
            public void onResponse(Call<ChangePasswordPut> call, Response<ChangePasswordPut> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                }
                Log.d(TAG, "onResponse: 성공");
                finish();
            }

            @Override
            public void onFailure(Call<ChangePasswordPut> call, Throwable t) {
                Toast.makeText(FindPasswordActivity2.this, "서버가 불안정합니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void phoneNumber(String phone){
        NumberPost numberPost = new NumberPost(phone);
        Call<NumberPost> call = NumberClient.getApiService().NumberCall(numberPost);
        call.enqueue(new Callback<NumberPost>() {
            @Override
            public void onResponse(Call<NumberPost> call, Response<NumberPost> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    Toast.makeText(FindPasswordActivity2.this, "다시 시도 해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onResponse: 성공"+response.body().toString());
            }

            @Override
            public void onFailure(Call<NumberPost> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
                return;
            }
        });
    }
}

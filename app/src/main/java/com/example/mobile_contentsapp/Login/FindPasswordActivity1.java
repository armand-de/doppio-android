package com.example.mobile_contentsapp.Login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_contentsapp.Login.Retrofit.FindPassword;
import com.example.mobile_contentsapp.Login.Retrofit.FindPasswordClient;
import com.example.mobile_contentsapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class FindPasswordActivity1 extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password1);

        getWindow().setStatusBarColor(Color.parseColor("#f1f2f3"));

        EditText phoneEdit = findViewById(R.id.phone_edit);

        Button nextBtn = findViewById(R.id.next_btn);
        ImageButton backBtn = findViewById(R.id.find_pass_back1);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next(phoneEdit.getText().toString());
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void next(String phone){
        FindPassword findPassword = new FindPassword(phone);
        Call<FindPassword> call = FindPasswordClient.getApiService().findPassword(findPassword);
        call.enqueue(new Callback<FindPassword>() {
            @Override
            public void onResponse(Call<FindPassword> call, Response<FindPassword> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: "+phone);
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    Toast.makeText(FindPasswordActivity1.this,"유효한 전화번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(FindPasswordActivity1.this ,FindPasswordActivity2.class);
                intent.putExtra("phone",phone);
                startActivity(intent);
                finish();
            }
            @Override
            public void onFailure(Call<FindPassword> call, Throwable t) {
                Toast.makeText(FindPasswordActivity1.this,"서버가 불안정합니다.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}

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

import com.example.mobile_contentsapp.Login.Retrofit.SignInClient;
import com.example.mobile_contentsapp.Login.Retrofit.SignInPost;
import com.example.mobile_contentsapp.Login.Retrofit.Authorization;
import com.example.mobile_contentsapp.R;
import com.example.mobile_contentsapp.Main.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;
import static com.example.mobile_contentsapp.Main.SplashActivity.tokenValue;

public class SignInActivity extends AppCompatActivity {

    private EditText nicknameEdit;
    private EditText passwordEdit;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        nicknameEdit = findViewById(R.id.nickname_edit);
        passwordEdit = findViewById(R.id.password_edit);

        Button findPass = findViewById(R.id.find_pass);
        Button signIn = findViewById(R.id.singinbtn);
        Button signUp = findViewById(R.id.signupbtn);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "개발 중인 기능입니다", Toast.LENGTH_SHORT).show();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isLoading = true;
                if (nicknameEdit.getText().toString().isEmpty() && passwordEdit.getText().toString().isEmpty()){
                    Toast.makeText(SignInActivity.this, "닉네임또는 비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                    isLoading = false;
                    return;
                }
                signin(nicknameEdit.getText().toString(), passwordEdit.getText().toString());
            }
        });
    }
    public void signin(String nick, String pass){

        SignInPost signInPost = new SignInPost(nick,pass);
        Call<Authorization> call = SignInClient.getApiService().signInCall(signInPost);

        call.enqueue(new Callback<Authorization>() {
            @Override
            public void onResponse(Call<Authorization> call, Response<Authorization> response) {
                if (!response.isSuccessful()){
                    Toast.makeText(SignInActivity.this, "닉네임또는 비밀번호가 올바르지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

                Authorization token = response.body();

                SharedPreferences sharedPreferences =  getSharedPreferences("sp",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                tokenValue = token.getAccessToken();
                Log.d(TAG, "토큰값"+ tokenValue);

                editor.putString("token",tokenValue);
                editor.commit();
                isLoading = false;
            }

            @Override
            public void onFailure(Call<Authorization> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 오류 "+t.getMessage());
            }
        });

    }

}
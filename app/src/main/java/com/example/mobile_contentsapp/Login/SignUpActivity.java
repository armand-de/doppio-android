package com.example.mobile_contentsapp.Login;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
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
import com.example.mobile_contentsapp.Login.Retrofit.CheckGet;
import com.example.mobile_contentsapp.Login.Retrofit.NumberClient;
import com.example.mobile_contentsapp.Login.Retrofit.NumberPost;
import com.example.mobile_contentsapp.Login.Retrofit.SignUpClient;
import com.example.mobile_contentsapp.Login.Retrofit.SignUpPost;
import com.example.mobile_contentsapp.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity {

    private boolean isVerify = false;
    private boolean exist = false;
    private boolean isLoading = false;

    private EditText nicknameEdit;
    private EditText passwordEdit;
    private EditText passConfirmEdit;
    private EditText phoneEdit;
    private EditText verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        getWindow().setStatusBarColor(Color.parseColor("#3F7972"));

        Button phoneBtn = findViewById(R.id.number_btn);
        Button signUpBtn = findViewById(R.id.sign_up_btn);
        TextView signInbtn = findViewById(R.id.sign_in_intent_btn);
        Button checkBtn = findViewById(R.id.check_nick_btn);

        TextInputLayout nickname_box = findViewById(R.id.nickname_inputbox);
        TextInputLayout password_box = findViewById(R.id.password_inputbox);

        nicknameEdit = nickname_box.getEditText();
        passwordEdit = password_box.getEditText();
        passConfirmEdit = findViewById(R.id.password_confirm_edit);
        phoneEdit = findViewById(R.id.phone_edit);
        verify = findViewById(R.id.number_edit);

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isLoading){
                    isLoading = true;
                    String nickname = nicknameEdit.getText().toString();
                    String password = passwordEdit.getText().toString();

                    if (nickname.length() >= 3 && nickname.length() <= 8 &&
                            password.length() >= 7 && password.length() <= 30 &&
                            passConfirmEdit.getText().toString().equals(passwordEdit.getText().toString())){

                        signUp(nickname, password, phoneEdit.getText().toString(),verify.getText().toString());
                    }else{
                        Toast.makeText(v.getContext(), "정확한 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        nicknameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                nickname_box.setError(null);
                if (nicknameEdit.getText().toString().length() < 3) {
                    nickname_box.setError("3자 이상 입력해주세요");
                }
                exist = false;
                checkBtn.setText("중복 확인");
                checkBtn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(SignUpActivity.this,R.color.purple_500)));
                checkBtn.setElevation(0);
                checkBtn.setTextColor(Color.parseColor("#ffffff"));
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
                password_box.setError(null);
                if (s.length() < 7) {
                    password_box.setError("7자 이상 입력해주세요");
                }
            }
        });

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pattern = "^01(?:0|1|[6-9])?(\\d{3}|\\d{4})?(\\d{4})$";
                if(Pattern.matches(pattern,phoneEdit.getText().toString())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this)
                            .setMessage("인증번호를 전송하시겠습니까?")
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    }
                            }).setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    phoneNumber(phoneEdit.getText().toString(),phoneBtn);
                                }
                            });
                    Dialog dialog = builder.create();
                    dialog.show();

                }else{
                    Log.d(TAG, "onClick: 불일치");
                    Toast.makeText(SignUpActivity.this, "유효한 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                }
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


        signInbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public void phoneNumber(String phone,Button btn){
        NumberPost number_post = new NumberPost(phone);
        Call<NumberPost> call = NumberClient.getApiService().NumberCall(number_post);
        call.enqueue(new Callback<NumberPost>() {
            @Override
            public void onResponse(Call<NumberPost> call, Response<NumberPost> response) {
                if(!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    Toast.makeText(SignUpActivity.this, "유효한 전화번호를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                Log.d(TAG, "onResponse: 성공"+response.body().toString());
                btn.setText("인증번호 재전송");
                isVerify = true;
            }

            @Override
            public void onFailure(Call<NumberPost> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
                return;
            }
        });
    }
    public void signUp(String nick , String pass, String phone, String verify){
        SignUpPost signUpPost = new SignUpPost(nick,pass,phone,verify);

        Call<SignUpPost> call = SignUpClient.getApiService().signUpCall(signUpPost);
        call.enqueue(new Callback<SignUpPost>() {
            @Override
            public void onResponse(Call<SignUpPost> call, Response<SignUpPost> response) {
                if (response.isSuccessful()){
                    Toast.makeText(SignUpActivity.this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();
                    finish();
                    isLoading = false;
                    return;
                }
                finish();
                Toast.makeText(SignUpActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SignUpPost> call, Throwable t) {
                finish();
                Toast.makeText(SignUpActivity.this, "회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void check(String nick, Button btn){
        Call<CheckGet> call = CheckClient.getApiService().checkCall(nick);
        call.enqueue(new Callback<CheckGet>() {
            @Override
            public void onResponse(Call<CheckGet> call, Response<CheckGet> response) {
                if (!response.isSuccessful()){
                    Log.d(TAG, "onResponse: 실패"+response.code());
                    return;
                }
                Log.d(TAG, "onResponse: 성공");
                CheckGet result = response.body();
                if (!result.isExist()){
                    exist = true;
                    btn.setText("확인 완료");
                    btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(SignUpActivity.this,R.color.purple_500)));
                    btn.setElevation(0);
                    btn.setTextColor(Color.parseColor("#ffffff"));
                }
            }

            @Override
            public void onFailure(Call<CheckGet> call, Throwable t) {
                Log.d(TAG, "onFailure: 시스템 에러" + t.getMessage());
            }
        });
    }
}
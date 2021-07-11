package com.example.mobile_contentsapp.Login;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_contentsapp.R;

public class FindPasswordActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password1);

        EditText phoneEdit = findViewById(R.id.phone_edit);
        EditText numberEdit = findViewById(R.id.number_edit);
        EditText passEdit = findViewById(R.id.password_edit);
        EditText confirmEdit = findViewById(R.id.password_confirm_edit);

        Button sentNumber = findViewById(R.id.number_btn);
        Button change = findViewById(R.id.change_btn);



    }
}

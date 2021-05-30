package com.example.mobile_contentsapp.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.mobile_contentsapp.R;

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

            }
        });

    }
}
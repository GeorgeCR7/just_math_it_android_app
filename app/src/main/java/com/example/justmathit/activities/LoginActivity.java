package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.justmathit.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnGoRegister;

    EditText edTxtLoginEmail, edTxtLoginPassword;

    TextView txtForgotPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        edTxtLoginEmail = findViewById(R.id.edTxtLoginEmail);
        edTxtLoginPassword = findViewById(R.id.edTxtLoginPassword);

        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        btnLogin.setOnClickListener(view -> {

        });

        btnGoRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        txtForgotPassword.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
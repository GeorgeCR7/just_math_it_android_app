package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.justmathit.R;

public class RegisterActivity extends AppCompatActivity {

    Button btnRegister, btnGoLogin;

    EditText edTxtRegisterEmail, edTxtRegisterPassword, edTxtRegisterPasswordAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        btnRegister = findViewById(R.id.btnRegister);
        btnGoLogin = findViewById(R.id.btnGoLogin);

        edTxtRegisterEmail = findViewById(R.id.edTxtRegisterEmail);
        edTxtRegisterPassword = findViewById(R.id.edTxtRegisterPassword);
        edTxtRegisterPasswordAgain = findViewById(R.id.edTxtRegisterPasswordAgain);

        btnRegister.setOnClickListener(view -> {

        });

        btnGoLogin.setOnClickListener(view -> {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

    }
}
package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.justmathit.R;

public class SuccessQuizActivity extends AppCompatActivity {

    Button btnOKBackMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_quiz);

        btnOKBackMain = findViewById(R.id.btnOKBackMain);

        btnOKBackMain.setOnClickListener(view -> {
            Intent intent = new Intent(SuccessQuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
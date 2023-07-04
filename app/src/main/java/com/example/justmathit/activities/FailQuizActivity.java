package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.justmathit.R;

public class FailQuizActivity extends AppCompatActivity {

    Button btnFailOKBackMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fail_quiz);

        btnFailOKBackMain = findViewById(R.id.btnFailOKBackMain);

        btnFailOKBackMain.setOnClickListener(view -> {
            Intent intent = new Intent(FailQuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
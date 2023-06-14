package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.justmathit.R;

public class CategoryActivity extends AppCompatActivity {

    TextView txtCategoryTitle;
    
    Button btnTheory, btnQuiz, btnBackProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        txtCategoryTitle = findViewById(R.id.txtCategoryTitle);

        btnTheory = findViewById(R.id.btnTheory);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnBackProfile = findViewById(R.id.btnBackProfile);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            txtCategoryTitle.setText(extras.getString("CATEGORY"));
            txtCategoryTitle.setPaintFlags(txtCategoryTitle.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        }

        btnTheory.setOnClickListener(view -> {

        });

        btnQuiz.setOnClickListener(view -> {

        });

        btnBackProfile.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
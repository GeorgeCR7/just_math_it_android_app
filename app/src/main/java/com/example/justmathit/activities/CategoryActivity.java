package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.justmathit.R;

public class CategoryActivity extends AppCompatActivity {

    TextView txtCategoryTitle;
    
    Button btnTheory, btnQuiz, btnBackCategory;

    String category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            category = extras.getString("CATEGORY");
        }

        txtCategoryTitle = findViewById(R.id.txtCategoryTitle);
        txtCategoryTitle.setText(category);
        txtCategoryTitle.setPaintFlags(txtCategoryTitle.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnTheory = findViewById(R.id.btnTheory);
        btnQuiz = findViewById(R.id.btnQuiz);
        btnBackCategory = findViewById(R.id.btnBackCategory);


        btnTheory.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, TheoryActivity.class);
            intent.putExtra("THEORY_TYPE", category);
            startActivity(intent);
            finish();
        });

        btnQuiz.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, QuizActivity.class);
            intent.putExtra("QUIZ_TYPE", category);
            startActivity(intent);
            finish();
        });

        btnBackCategory.setOnClickListener(view -> {
            Intent intent = new Intent(CategoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
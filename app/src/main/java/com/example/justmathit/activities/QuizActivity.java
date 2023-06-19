package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.justmathit.R;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuizTypeLabel, txtQuestionNumber, txtQuestionPronun;

    RadioGroup rdGroupOptions;
    RadioButton rdBtnOptionA, rdBtnOptionB, rdBtnOptionC;

    Button btnOKQuestion, btnBackQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuizTypeLabel = findViewById(R.id.txtQuizTypeLabel);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            txtQuizTypeLabel.setText(extras.getString("QUIZ_TYPE"));
        }

        txtQuestionNumber = findViewById(R.id.txtQuestionNumber);
        txtQuestionNumber.setText(getString(R.string.question_label));

        txtQuestionPronun = findViewById(R.id.txtQuestionPronun);

        rdGroupOptions = findViewById(R.id.rdGroupOptions);
        rdBtnOptionA = findViewById(R.id.rdBtnOptionA);
        rdBtnOptionB = findViewById(R.id.rdBtnOptionB);
        rdBtnOptionC = findViewById(R.id.rdBtnOptionC);

        btnOKQuestion = findViewById(R.id.btnOKQuestion);
        btnBackQuiz = findViewById(R.id.btnBackQuiz);


        btnOKQuestion.setOnClickListener(view -> {
            finish();
            startActivity(getIntent());
        });

        btnBackQuiz.setOnClickListener(view -> {
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }
}
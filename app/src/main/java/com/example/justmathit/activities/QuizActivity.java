package com.example.justmathit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.justmathit.R;
import com.example.justmathit.models.Question;
import com.example.justmathit.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class QuizActivity extends AppCompatActivity {

    TextView txtQuizTypeLabel, txtQuestionNumber, txtQuestionPronun;

    RadioGroup rdGroupOptions;
    RadioButton rdBtn;

    Button btnOKQuestion, btnBackQuiz;

    String quizType;

    int questionID;

    // Firebase objects for reading database & authentication.
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    FirebaseAuth mAuth;

    private ArrayList<User> users;
    private ArrayList<Question> questions;
    private ArrayList<String> selectedAnswers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        txtQuizTypeLabel = findViewById(R.id.txtQuizTypeLabel);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            quizType = extras.getString("QUIZ_TYPE");
            txtQuizTypeLabel.setText(quizType);
            txtQuizTypeLabel.setPaintFlags(txtQuizTypeLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        txtQuestionNumber = findViewById(R.id.txtQuestionNumber);
        txtQuestionNumber.setText(getString(R.string.question_label));
        txtQuestionNumber.setPaintFlags(txtQuestionNumber.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txtQuestionPronun = findViewById(R.id.txtQuestionPronun);

        rdGroupOptions = findViewById(R.id.rdGroupOptions);

        btnOKQuestion = findViewById(R.id.btnOKQuestion);
        btnBackQuiz = findViewById(R.id.btnBackQuiz);

        users = new ArrayList<>();
        questions = new ArrayList<>();
        selectedAnswers = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        selectedAnswers.clear();

        // Init first question of the current quiz type.
        if (quizType.equals(getString(R.string.equationsA))){
            questionID = 1;
            createQuizUI(questionID);
        } else if(quizType.equals(getString(R.string.equationsB))){
            questionID = 6;
            createQuizUI(questionID);
        } else {
            questionID = 11;
            createQuizUI(questionID);
        }

        btnOKQuestion.setOnClickListener(view -> {
            questionID++;
            rdGroupOptions.clearCheck();
            createQuizUI(questionID);
            if (quizType.equals(getString(R.string.equationsA)) && questionID == 6) {
                checkIfTestPassed(selectedAnswers);
            } else if (quizType.equals(getString(R.string.equationsB)) && questionID == 11){
                checkIfTestPassed(selectedAnswers);
            } else if (quizType.equals(getString(R.string.suitability_actions)) && questionID == 16){
                checkIfTestPassed(selectedAnswers);
            }
        });

        btnBackQuiz.setOnClickListener(view -> {
            Intent intent = new Intent(QuizActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void createQuizUI (int questionID){

        reference = rootNode.getReference().child("Questions");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Question question = dataSnapshot.getValue(Question.class);
                    questions.add(question);
                }
                for (Question q : questions){
                    if (Integer.parseInt(q.getQuestionID()) == questionID){
                        txtQuestionPronun.setText(q.getPronun());
                        ((RadioButton) rdGroupOptions.getChildAt(0)).setText(q.getOption1());
                        ((RadioButton) rdGroupOptions.getChildAt(1)).setText(q.getOption2());
                        ((RadioButton) rdGroupOptions.getChildAt(2)).setText(q.getOption3());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void checkSelectedBtn(View view) {
        // Get the selection of the user.
        int selectedRadioId = rdGroupOptions.getCheckedRadioButtonId();
        rdBtn = findViewById(selectedRadioId);
        selectedAnswers.add(rdBtn.getText().toString().substring(0,1));
    }

    private void checkIfTestPassed (ArrayList<String> selectedAnswers){

        if (computeFinalResult(selectedAnswers)){
            findUserPassedQuizzes();
            Intent intent = new Intent(QuizActivity.this, SuccessQuizActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(QuizActivity.this, FailQuizActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean computeFinalResult(ArrayList<String> selectedAnswers) {

        ArrayList<String> answersTestA, answersTestB, answersTestC;

        answersTestA = new ArrayList<>();
        Collections.addAll(answersTestA, "Α", "Γ", "Β", "Β", "Β");

        answersTestB = new ArrayList<>();
        Collections.addAll(answersTestB, "Α", "Γ", "Β", "Γ", "Α");

        answersTestC = new ArrayList<>();
        Collections.addAll(answersTestC, "Α", "Γ", "Β", "Α", "Β");

        if (quizType.equals(getString(R.string.equationsA))) {
            return selectedAnswers.equals(answersTestA);
        } else if (quizType.equals(getString(R.string.equationsB))){
            return selectedAnswers.equals(answersTestB);
        } else if (quizType.equals(getString(R.string.suitability_actions))){
            return selectedAnswers.equals(answersTestC);
        }

        return false;
    }

    private void findUserPassedQuizzes() {

        reference = rootNode.getReference().child("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    users.add(user);
                }
                for (User user: users){
                    if(user.getEmail().equals(mAuth.getCurrentUser().getEmail())) {
                        updateUserPassedQuizzes(user.getQuizzesPassed());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void updateUserPassedQuizzes(String userQuizzesPassed) {

        HashMap hashMap = new HashMap();

        if (quizType.equals(getString(R.string.equationsA))){
            if (userQuizzesPassed.contains("1")){
                hashMap.put("quizzesPassed", userQuizzesPassed + "");
            } else {
                hashMap.put("quizzesPassed", userQuizzesPassed + "1");
            }
        } else if(quizType.equals(getString(R.string.equationsB))){
            if (userQuizzesPassed.contains("2")){
                hashMap.put("quizzesPassed", userQuizzesPassed + "");
            } else {
                hashMap.put("quizzesPassed", userQuizzesPassed + "2");
            }
        } else {
            if (userQuizzesPassed.contains("3")){
                hashMap.put("quizzesPassed", userQuizzesPassed + "");
            } else {
                hashMap.put("quizzesPassed", userQuizzesPassed + "3");
            }
        }

        reference = rootNode.getReference().child("Users");
        reference.child(mAuth.getCurrentUser().getEmail().replace(".","")).
                updateChildren(hashMap)
                .addOnSuccessListener(o -> {});
    }
}
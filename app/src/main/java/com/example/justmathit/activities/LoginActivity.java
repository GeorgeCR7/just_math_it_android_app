package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justmathit.R;
import com.example.justmathit.models.Question;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin, btnGoRegister;

    EditText edTxtLoginEmail, edTxtLoginPassword;

    TextView txtForgotPassword;

    FirebaseAuth mAuth;





    // Firebase objects for reading database.
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private ArrayList<Question> questions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = findViewById(R.id.btnLogin);
        btnGoRegister = findViewById(R.id.btnGoRegister);

        edTxtLoginEmail = findViewById(R.id.edTxtLoginEmail);
        edTxtLoginPassword = findViewById(R.id.edTxtLoginPassword);

        txtForgotPassword = findViewById(R.id.txtForgotPassword);

        mAuth = FirebaseAuth.getInstance();

        questions = new ArrayList<>();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });

        btnGoRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        txtForgotPassword.setOnClickListener(view -> {

            reference = FirebaseDatabase.getInstance().getReference("Questions");

            // Quiz 1 - Equations A.
            Question q1 = new Question("1", "3x = 6", "A. x = 2", "Β. x = 5", "Γ. x = -1", "Α");
            Question q2 = new Question("2", "7x = 20-13", "A. x = 0", "Β. x = 7", "Γ. x = 1", "Γ");
            Question q3 = new Question("3", "5x - 2x = 12 - 9", "A. x = 0", "Β. x = 1", "Γ. x = 9", "B");
            Question q4 = new Question("4", "3(x + 10) = 110 - x", "A. x = 25", "Β. x = 20", "Γ. x = -10", "B");
            Question q5 = new Question("5", "10(2x - x) + 5 = 5(7x - 6x) + 5", "A. x = 2", "Β. x = 0", "Γ. x = -5", "Β");
            questions.add(q1);
            questions.add(q2);
            questions.add(q3);
            questions.add(q4);
            questions.add(q5);

            // Quiz 2 - Equations B.
            Question q6 = new Question("6", "x^2 = 9", "A. x = +/-3", "Β. x = +/-9", "Γ. x = 0", "Α");
            Question q7 = new Question("7", "2x^2 + 5x^2 = 50-30-10", "A. x = +/-2", "Β. x = +/-7", "Γ. x = +/-1", "Γ");
            Question q8 = new Question("8", "5x^2 - x^2 + 10 = 3x^2 - x^2 + 110", "A. x = 0", "Β. x = 1", "Γ. x = 9", "B");
            Question q9 = new Question("9", "3(x - 10) = 120 - x", "A. x = 2", "Β. x = 5", "Γ. x = -1", "Α");
            Question q10 = new Question("10", "10(2x - x) + 5 = 5(7x - 6x) + 5", "A. x = 2", "Β. x = 5", "Γ. x = -1", "Α");
            questions.add(q6);
            questions.add(q7);
            questions.add(q8);
            questions.add(q9);
            questions.add(q10);

            // Quiz 3 - Suitability.
            Question q11 = new Question("11",
                    "Μετά τις δυναμείς ακολοθούν...",
                    "A. Πολλαπλασιασμοί και διαιρέσεις.",
                    "Β. Πράξεις μέσα στις παρενθέσεις.",
                    "Γ. Αφαιρέσεις.", "Α");
            Question q12 = new Question("12", "2x^2 + 5x^2 = 50-30-10", "A. x = +/-2", "Β. x = +/-7", "Γ. x = +/-1", "Γ");
            Question q13 = new Question("13", "5x^2 - x^2 + 10 = 3x^2 - x^2 + 110", "A. x = 0", "Β. x = 1", "Γ. x = 9", "B");
            Question q14 = new Question("14", "3(x - 10) = 120 - x", "A. x = 2", "Β. x = 5", "Γ. x = -1", "Α");
            Question q15 = new Question("15", "10(2x - x) + 5 = 5(7x - 6x) + 5", "A. x = 2", "Β. x = 5", "Γ. x = -1", "Α");
            questions.add(q11);
            questions.add(q12);
            questions.add(q13);
            questions.add(q14);
            questions.add(q15);


            for (Question q : questions) {
                reference.child("Question " + q.getQuestionID()).setValue(q);
            }
            /*Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();*/
        });
    }

    private void loginUser(){

        String email = edTxtLoginEmail.getText().toString();
        String password = edTxtLoginPassword.getText().toString();

        if (email.isEmpty()) {
            edTxtLoginEmail.setError(getResources().getString(R.string.email_empty));
            edTxtLoginEmail.requestFocus();
            return;
        } else if (!isEmailValid(email)) {
            edTxtLoginEmail.setError(getResources().getString(R.string.email_not_valid));
            edTxtLoginEmail.requestFocus();
            return;
        } else if (password.isEmpty()){
            edTxtLoginPassword.setError(getResources().getString(R.string.password_empty));
            edTxtLoginPassword.requestFocus();
            return;
        } else {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this,
                            R.string.log_in_success,
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this,
                            R.string.log_in_error + "" + task.getException(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private static boolean isEmailValid (String email) {

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null) {
            return false;
        }
        return pat.matcher(email).matches();
    }
}
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
import com.example.justmathit.models.Theory;
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
    private DatabaseReference reference;

    private ArrayList<Question> questions;

    private ArrayList<Theory> theoryList;


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
        theoryList = new ArrayList<>();

        btnLogin.setOnClickListener(view -> {
            loginUser();
        });

        btnGoRegister.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
            finish();
        });

        txtForgotPassword.setOnClickListener(view -> {

            /*reference = FirebaseDatabase.getInstance().getReference("Questions");

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
            Question q8 = new Question("8", "2x^2 + 5x + 3 = 0", "A. x1 = 0, x2 = 5", "Β. x1 = -1, x2= -3/2", "Γ. x1 = 9, x2 = -5/3", "B");
            Question q9 = new Question("9", "-16x^2 + 8x -1 = 0", "A. x1 = 2, x2 = -1", "Β. x = 7/2", "Γ. x = 1/4", "Γ");
            Question q10 = new Question("10", "2x^2 - 8x + 6 = 0", "A. x1 = 3, x2 = 1", "Β. x1 = 5, x2 = 0", "Γ. x1 = -1, x2 = 4", "Α");
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

            Question q12 = new Question("12",
                    "Κατά σειρά, πρώτο πρέπει να κάνουμε...",
                    "A. Δυνάμεις.",
                    "Β. Προσθέσεις ή αφαιρέσεις.",
                    "Γ. Παρανθέσεις", "Γ");

            Question q13 = new Question("13",
                    "Τελευταίο κατά σειρά, κάνουμε τις...",
                    "A. Παρανθέσεις.",
                    "Β. Προσθέσεις ή αφαιρέσεις.",
                    "Γ. Δυνάμεις.", "B");

            Question q14 = new Question("14",
                    "Να επιλυθεί η παράσταση\n3*5 - 5*2 + 2*3 + 4*9 - 8:2 - 6:3",
                    "A. x = 41",
                    "Β. x = 25",
                    "Γ. x = -10", "Α");

            Question q15 = new Question("15",
                    "Να επιλυθεί η παράσταση\n4*(12-9) + 3*(8-6):(5+1)",
                    "A. x = 20",
                    "Β. x = 13",
                    "Γ. x = 8", "B");
            questions.add(q11);
            questions.add(q12);
            questions.add(q13);
            questions.add(q14);
            questions.add(q15);


            for (Question q : questions) {
                reference.child("Question " + q.getQuestionID()).setValue(q);
            }*/

            reference = FirebaseDatabase.getInstance().getReference("Theory");

            Theory t1 = new Theory(1,
                    "Για την επίλυση μίας εξίσωσης Α' βαθμού: \n" +
                            "1. Απαλοίφουμε τους παρονομαστές, αν υπάρχουν.\n" +
                            "2. Κάνουμε τους πολλαπλασιασμούς, αν υπάρχουν.\n" +
                            "3. Χωρίζουμε γνωστούς, από αγνώστους.\n" +
                            "4. Κάνουμε αναγωγή όμοιων όρων (πρόσθεση)\n" +
                            "5. Διαιρούμε και τα δύο μέλη με το συντελεστή τού αγνώστου.");

            Theory t2 = new Theory(2,
                    "Για την επίλυση μίας εξίσωσης Β' βαθμού:\n" +
                            "Έστω η δευτεροβάθμια εξίσωση: αx^2 + βx + γ = 0. \n" +
                            "Υπολογίζουμε την διακρίνουσα: Δ = β^2 - 4αγ.\n" +
                            "Λύσεις της εξίσωσης:\n" +
                            "- Αν Δ>0 => x1,2 = -β+-ρίζα(Δ)/2α, η εξίσωση έχει 2 ρίζες.\n" +
                            "- Αν Δ=0 => x1,2 = -β/2α, η εξίσωση έχει μία διπλή ρίζα.\n" +
                            "- Αν Δ<0 => δεν υπάρχουν πραγματικές ρίζες.");

            Theory t3 = new Theory(3,
                    "Η σειρά των πράξεων, είναι η παρακάτω:\n" +
                            "1. Παρενθέσεις.\n" +
                            "2. Δυνάμεις και ρίζες.\n" +
                            "3. Πολλαπλασιασμοί και διαιρέσεις.\n" +
                            "4. Προσθέσεις και αφαιρέσεις.");

            theoryList.add(t1);
            theoryList.add(t2);
            theoryList.add(t3);


            for (Theory theory : theoryList) {
                reference.child("Theory " + theory.getTheoryID()).setValue(theory);
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
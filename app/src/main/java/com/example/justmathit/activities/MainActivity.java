package com.example.justmathit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justmathit.models.User;
import com.example.justmathit.pop_ups.AboutWindow;
import com.example.justmathit.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtAppTitle, txtDate;

    Button btnEquationsA, btnEquationsB, btnSuitability;
    Button btnLogOut, btnAbout, btnProfile;

    // Firebase objects for reading database & authentication.
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    FirebaseAuth mAuth;

    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAppTitle = findViewById(R.id.txtAppTitle);
        colorMainAppTitle(txtAppTitle.getText().toString());

        txtDate = findViewById(R.id.txtDate);
        txtDate.setText(setCurrentDate());

        btnEquationsA = findViewById(R.id.btnEquationsA);
        btnEquationsB = findViewById(R.id.btnEquationsB);
        btnSuitability = findViewById(R.id.btnSuitability);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnAbout = findViewById(R.id.btnAbout);
        btnProfile = findViewById(R.id.btnProfile);

        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        users = new ArrayList<>();

        disableProperButtons();

        btnEquationsA.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("CATEGORY", btnEquationsA.getText().toString());
            startActivity(intent);
            finish();
        });

        btnEquationsB.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("CATEGORY", btnEquationsB.getText().toString());
            startActivity(intent);
            finish();
        });

        btnSuitability.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, CategoryActivity.class);
            intent.putExtra("CATEGORY", btnSuitability.getText().toString());
            startActivity(intent);
            finish();
        });

        btnAbout.setOnClickListener(view -> {
            showAboutWindow();
        });

        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
            finish();
        });

        btnLogOut.setOnClickListener(view -> {
            mAuth.signOut();
            Toast.makeText(MainActivity.this,
                    R.string.log_out_main,
                    Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void disableProperButtons() {

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
                        List<String> myList = new ArrayList<>(Arrays.asList(user.getQuizzesPassed().split("")));
                        if (myList.contains("1")){
                            btnEquationsA.setBackgroundColor(Color.GREEN);
                            btnEquationsA.setTextColor(Color.WHITE);
                            btnEquationsA.setEnabled(false);
                        }
                        if (myList.contains("2")){
                            btnEquationsB.setBackgroundColor(Color.GREEN);
                            btnEquationsB.setTextColor(Color.WHITE);
                            btnEquationsB.setEnabled(false);
                        }
                        if (myList.contains("3")){
                            btnSuitability.setBackgroundColor(Color.GREEN);
                            btnSuitability.setTextColor(Color.WHITE);
                            btnSuitability.setEnabled(false);
                        }
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    private void showAboutWindow () {
        AboutWindow aboutWindow = new AboutWindow();
        aboutWindow.show(getSupportFragmentManager(), "About Window");
    }

    private void colorMainAppTitle (String strTxtAppTitle) {
        SpannableString ss = new SpannableString(strTxtAppTitle);
        ForegroundColorSpan fcsRed = new ForegroundColorSpan(Color.RED);
        ForegroundColorSpan fcsBlue = new ForegroundColorSpan(Color.BLUE);
        ForegroundColorSpan fcsGreen = new ForegroundColorSpan(Color.GREEN);
        ss.setSpan(fcsRed, 0,4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsBlue, 5,9, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(fcsGreen, 10,12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtAppTitle.setText(ss);
    }

    private String setCurrentDate(){

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        String dayName = null, day, month;

        if(cal.get(Calendar.DAY_OF_MONTH) < 10) {
            day = "0" + cal.get(Calendar.DAY_OF_MONTH)+".";
        } else {
            day = ""+cal.get(Calendar.DAY_OF_MONTH)+".";
        }

        if((cal.get(Calendar.MONTH)+1) < 10) {
            month = "0" + (cal.get(Calendar.MONTH)+1)+".";
        } else {
            month = ""+(cal.get(Calendar.MONTH)+1)+".";
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate today = LocalDate.now();
            DayOfWeek dayOfWeek = today.getDayOfWeek();
            dayName = dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault());
        }

        if (dayName.equals("Sunday")) {
            dayName = getResources().getString(R.string.sunday);
        } else if (dayName.equals("Monday")) {
            dayName = getResources().getString(R.string.monday);
        } else if (dayName.equals("Tuesday")) {
            dayName = getResources().getString(R.string.tuesday);
        } else if (dayName.equals("Wednesday")) {
            dayName = getResources().getString(R.string.wednesday);
        } else if (dayName.equals("Thursday")) {
            dayName = getResources().getString(R.string.thursday);
        } else if (dayName.equals("Friday")) {
            dayName = getResources().getString(R.string.friday);
        } else if (dayName.equals("Saturday")) {
            dayName = getResources().getString(R.string.saturday);
        }

        return dayName + ", " +day + month + cal.get(Calendar.YEAR);
    }
}
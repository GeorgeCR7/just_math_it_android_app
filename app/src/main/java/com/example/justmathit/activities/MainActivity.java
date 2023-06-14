package com.example.justmathit.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.justmathit.AboutWindow;
import com.example.justmathit.R;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtAppTitle, txtDate;

    Button btnEquationsA, btnEquationsB, btnSuitability;
    Button btnLogOut, btnAbout, btnProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtAppTitle = findViewById(R.id.txtAppTitle);
        txtDate = findViewById(R.id.txtDate);
        txtDate.setText(setCurrentDate());

        btnEquationsA = findViewById(R.id.btnEquationsA);
        btnEquationsB = findViewById(R.id.btnEquationsB);
        btnSuitability = findViewById(R.id.btnSuitability);
        btnLogOut = findViewById(R.id.btnLogOut);
        btnAbout = findViewById(R.id.btnAbout);
        btnProfile = findViewById(R.id.btnProfile);

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

        });
    }

    private void showAboutWindow () {
        AboutWindow aboutWindow = new AboutWindow();
        aboutWindow.show(getSupportFragmentManager(), "About Window");
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
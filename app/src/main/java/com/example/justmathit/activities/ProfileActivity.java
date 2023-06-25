package com.example.justmathit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.justmathit.R;
import com.example.justmathit.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {

    Button btnSaveChanges, btnBackProfile;

    TextView txtPrfEmailLabel, txtPrfDateCreatedLabel;
    TextView txtPrfEmailValue, txtPrfDateCreatedValue;

    EditText edTxtPrfNameValue, edTxtPrfAgeValue, edTxtPrfCountryValue;

    // Firebase objects for reading database & authentication.
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    FirebaseAuth mAuth;

    // List with all users from database.
    private ArrayList<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        btnBackProfile = findViewById(R.id.btnBackProfile);

        // Init & underline label.
        txtPrfEmailLabel = findViewById(R.id.txtPrfEmailLabel);
        txtPrfEmailLabel.setPaintFlags(txtPrfEmailLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Init & underline label.
        txtPrfDateCreatedLabel = findViewById(R.id.txtPrfDateCreatedLabel);
        txtPrfDateCreatedLabel.setPaintFlags(txtPrfDateCreatedLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        txtPrfEmailValue = findViewById(R.id.txtPrfEmailValue);
        txtPrfDateCreatedValue = findViewById(R.id.txtPrfDateCreatedValue);

        edTxtPrfNameValue = findViewById(R.id.edTxtPrfNameValue);
        edTxtPrfAgeValue = findViewById(R.id.edTxtPrfAgeValue);
        edTxtPrfCountryValue = findViewById(R.id.edTxtPrfCountryValue);

        users = new ArrayList<>();

        rootNode = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        initProfileActivity();

        btnSaveChanges.setOnClickListener(view -> {
            updateUserProfile();
        });

        btnBackProfile.setOnClickListener(view -> {
            Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void initProfileActivity() {

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
                        txtPrfEmailValue.setText(user.getEmail());
                        txtPrfDateCreatedValue.setText(user.getDateCreated());
                        edTxtPrfNameValue.setText(user.getName());
                        edTxtPrfAgeValue.setText(user.getAge());
                        edTxtPrfCountryValue.setText(user.getCountry());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void updateUserProfile() {

        String name = edTxtPrfNameValue.getText().toString();
        String age = edTxtPrfAgeValue.getText().toString();
        String country = edTxtPrfCountryValue.getText().toString();
        HashMap hashMap = new HashMap();
        hashMap.put("name", name);
        hashMap.put("age", age);
        hashMap.put("country", country);

        reference = rootNode.getReference().child("Users");
        reference.child(mAuth.getCurrentUser().getEmail().replace(".","")).
                updateChildren(hashMap)
                .addOnSuccessListener(o -> Toast.makeText(ProfileActivity.this,
                        R.string.update_user_profile, Toast.LENGTH_SHORT).show());
        finish();
        startActivity(getIntent());
    }
}
package com.example.justmathit.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.justmathit.R;
import com.example.justmathit.models.Question;
import com.example.justmathit.models.Theory;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TheoryActivity extends AppCompatActivity {

    TextView txtTheoryMainLabel, txtTheoryContent;

    Button btnBackTheory;

    String theoryType;

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;

    private ArrayList<Theory> theoryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory);

        txtTheoryMainLabel = findViewById(R.id.txtTheoryMainLabel);
        Bundle extras = getIntent().getExtras();
        if (extras != null){
            theoryType = extras.getString("THEORY_TYPE");
            txtTheoryMainLabel.setText(theoryType);
            txtTheoryMainLabel.setPaintFlags(txtTheoryMainLabel.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        }

        txtTheoryContent = findViewById(R.id.txtTheoryContent);

        btnBackTheory = findViewById(R.id.btnBackTheory);

        theoryList = new ArrayList<>();

        if (theoryType.equals(getString(R.string.equationsA))){
            createTheoryUI(1);
        } else if(theoryType.equals(getString(R.string.equationsB))){
            createTheoryUI(2);
        } else {
            createTheoryUI(3);
        }

        btnBackTheory.setOnClickListener(view -> {
            Intent intent = new Intent(TheoryActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private void createTheoryUI(int theoryID){

        reference = rootNode.getReference().child("Theory");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Theory theory = dataSnapshot.getValue(Theory.class);
                    theoryList.add(theory);
                }
                for (Theory t : theoryList){
                    if (t.getTheoryID() == theoryID){
                        txtTheoryContent.setText(t.getTheoryContent());
                    }
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}
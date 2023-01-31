package com.example.onlinevote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainMenu extends AppCompatActivity {

    FirebaseAuth mAuth;
    Button logout;
    String userKey, phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        userKey = getIntent().getStringExtra("userKey").trim();
        phoneNumber = getIntent().getStringExtra("phoneNumber").trim();
//        HashMap Voter = new HashMap();
//        Voter.put("userKey", userKey);
//        FirebaseDatabase.getInstance().getReference("User").child(phoneNumber)
//                .updateChildren(Voter);
        Intent b = new Intent(MainMenu.this, VoterHome.class);
        b.putExtra("phoneNumber", phoneNumber);
        startActivity(b);
        finishAndRemoveTask();
    }
}
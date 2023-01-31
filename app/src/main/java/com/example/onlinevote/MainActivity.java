package com.example.onlinevote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b1 = (Button) findViewById(R.id.btnSignIn);
        b2 = (Button) findViewById(R.id.btnSignUp);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openLogin();
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { openSignUp();}
        });

    }
    public void openLogin(){
        Intent intent = new Intent(this, SignIn.class);
        startActivity(intent);
    }
    public void openSignUp(){
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
//        if(currentUser != null)
//        {
//            startActivity(new Intent(MainActivity.this, VoterHome.class));
//            finish();
//        }
//    }
}
package com.example.onlinevote;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminHome extends AppCompatActivity {

    Button b1, b2, b3, b4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        b1 = (Button) findViewById(R.id.Vote);
        b2 = (Button) findViewById(R.id.viewParty);
        b3 = (Button) findViewById(R.id.result);
        b4 = (Button) findViewById(R.id.LogOut);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddvoter();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, AddParty.class);
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminHome.this, ResultActivity.class);
                startActivity(intent);

            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminHome.this);
                builder.setTitle("Alert").
                        setMessage("Are you want to logout?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                finish();
                                startActivity(i);
                            }
                        });
                builder.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) { dialogInterface.cancel(); }
                        });

                AlertDialog alert1 = builder.create();
                alert1.show();
            }
        });

    }

    private void openAddvoter() {
        Intent intent = new Intent(this, AddVoter.class);
        startActivity(intent);
    }
}
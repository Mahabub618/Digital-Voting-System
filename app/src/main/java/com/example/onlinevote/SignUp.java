package com.example.onlinevote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SignUp extends AppCompatActivity {


    EditText e1, e2, e5;
    TextInputLayout e3, e4;
    Button b1;
    DatabaseReference db;
    FirebaseAuth mAuth;
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        e1 = (EditText) findViewById(R.id.voterName);
        e2 = (EditText) findViewById(R.id.NidNumber);
        e3 = (TextInputLayout) findViewById(R.id.RegisterPassword);
        e4 = (TextInputLayout) findViewById(R.id.ConfirmPassword);
        e5 = (EditText) findViewById(R.id.voterPhone);

        b1 = (Button) findViewById(R.id.submit);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("User");


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String vName = e1.getText().toString().trim();
                String vNid = e2.getText().toString().trim();
                String vPass = e3.getEditText().getText().toString().trim();
                String cPass = e4.getEditText().getText().toString().trim();
                String vPho = e5.getText().toString().trim();

                if(vName.isEmpty()){
                    e1.setError("This field cannot be empty");
                    e1.requestFocus();
                }
                else if(vNid.isEmpty()){
                    e2.setError("This field cannot be empty");
                    e2.requestFocus();
                }
                else if(vPass.isEmpty()){
                    e3.setError("This field cannot be empty");
                    e3.requestFocus();
                }
                else if(cPass.isEmpty()){
                    e4.setError("This field cannot be empty");
                    e4.requestFocus();
                }
                else if(vPho.isEmpty()){
                    e5.setError("This field cannot be empty");
                    e5.requestFocus();
                }


                if(vPass.equals(cPass) && !vPass.isEmpty()){

                    if(vNid.length() == 10 && vPho.length() == 11){

                        ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                        mDialog.setMessage("Please Waiting...");
                        mDialog.show();
                        String phone = "+88" + vPho;
                        fb.getInstance().collection("User").document(phone)
                                .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.getResult().exists())
                                {
                                    e5.setError("Phone no. already exist");
                                    e5.requestFocus();
                                    mDialog.dismiss();
                                }
                                else
                                {
                                    String UserKey = "123";
                                    User user = new User(vName, vNid, vPass, UserKey, 0, 0);
                                    fb.getInstance().collection("User").document(phone).set(user)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    mDialog.dismiss();
                                                    Toast.makeText(SignUp.this, "Data Saved!", Toast.LENGTH_SHORT).show();


                                                    Intent b = new Intent(SignUp.this, VerifyPhone.class);
                                                    b.putExtra("phoneNumber", phone);
                                                    b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(b);

                                                    e1.setText("");
                                                    e2.setText("");
                                                    e5.setText("");
                                                    finish();
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mDialog.dismiss();
                                            Toast.makeText(SignUp.this, "Data Failed!!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }
                        });

//                        db.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                if(snapshot.child(vPho).exists()){
//                                    e5.setError("Phone no. already exist");
//                                    e5.requestFocus();
//                                    mDialog.dismiss();
//                                }
//                                else{
//                                    //String UserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
////                                    String UserKey = "abcdef";
//                                    String phoneNumber = "+88" + vPho;
////                                    User user = new User(vName, vNid, vPass, UserKey, 0, 0);
////                                    db.child(phoneNumber).setValue(user);
//                                    //Toast.makeText(SignUp.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                                    mDialog.dismiss();
//
//                                    Intent b = new Intent(SignUp.this, VerifyPhone.class);
//                                    b.putExtra("phoneNumber", phoneNumber);
//                                    b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(b);
//
//                                    e1.setText("");
//                                    e2.setText("");
//                                    e5.setText("");
//                                    finish();
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
                    }
                    else if(vPho.length() != 11){
                        e5.setError("Valid Phone no. required");
                        e5.requestFocus();
                    }
                    else{
                        e2.setError("Valid Nid no. required");
                        e2.requestFocus();
                    }
                }
                else if(!vPass.isEmpty()){
                    e4.setError("Password not matched!");
                    e4.requestFocus();
                }
            }
        });

    }

}
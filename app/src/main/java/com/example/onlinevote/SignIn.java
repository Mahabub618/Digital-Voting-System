package com.example.onlinevote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.hbb20.CountryCodePicker;

public class SignIn extends AppCompatActivity {

    Button signin;
    TextInputLayout num, voterpass;
    FirebaseAuth Fauth;
    String pass, number;
    CountryCodePicker cpp;
    TextView signup;
    DatabaseReference reference;
    FirebaseFirestore fb;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

//        Toast.makeText(this, "Sign In class!!", Toast.LENGTH_SHORT).show();

        signin = (Button) findViewById(R.id.Login);
        num = (TextInputLayout) findViewById(R.id.LoginNid);
        voterpass = (TextInputLayout) findViewById(R.id.LoginPassword);
        cpp = (CountryCodePicker)findViewById(R.id.CountryCode);
        signup = (TextView) findViewById(R.id.noAccount);
        Fauth = FirebaseAuth.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                number = num.getEditText().getText().toString().trim();
                String PhoneNumber = cpp.getSelectedCountryCodeWithPlus()+number;
                pass = voterpass.getEditText().getText().toString().trim();
                String user = "0" + number;

                if(isValid()) {

                    final ProgressDialog mDialog = new ProgressDialog(SignIn.this);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setCancelable(false);
                    mDialog.setMessage("Sign In Please Wait....");
                    mDialog.show();

                    FirebaseFirestore.getInstance().collection("User").document(PhoneNumber).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot document = task.getResult();
                                if(document != null && document.exists())
                                {
                                    String password = document.getString("password");
                                    String usertype = document.get("usertype").toString();

                                    mDialog.dismiss();

                                    if (pass.equals(password) && !password.isEmpty())
                                    {
                                        Intent b = new Intent(SignIn.this, SendOtp.class);
                                        Bundle extras = new Bundle();
                                        extras.putString("PhoneNumber", PhoneNumber);
                                        extras.putString("usertype", usertype);
                                        b.putExtras(extras);
                                        startActivity(b);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(SignIn.this, "Entered Wrong Password!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else
                                {
                                    mDialog.dismiss();
                                    Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();
                                    num.requestFocus();
                                }
                            }
                        }
                    });


//                    FirebaseDatabase.getInstance().getReference("User").addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            if (snapshot.child(PhoneNumber).exists()) {
//                                reference = FirebaseDatabase.getInstance().getReference("User").child(PhoneNumber);
//                                reference.addValueEventListener(new ValueEventListener() {
//
//                                    @Override
//                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                        String password = snapshot.child("password").getValue().toString();
//                                        String usertype = snapshot.child("usertype").getValue().toString();
//
//                                        mDialog.dismiss();
//
//                                        if (pass.equals(password) && !password.isEmpty()) {
//
//                                            Intent b = new Intent(SignIn.this, SendOtp.class);
//                                            Bundle extras = new Bundle();
//                                            extras.putString("PhoneNumber", PhoneNumber);
//                                            extras.putString("usertype", usertype);
//                                            b.putExtras(extras);
//
////                                            Intent b = new Intent(SignIn.this, VoterHome.class);
////                                            b.putExtra("phoneNumber", PhoneNumber);
//
//                                            startActivity(b);
//                                            finish();
//                                        } else {
//                                            Toast.makeText(SignIn.this, "Entered Wrong Password!", Toast.LENGTH_SHORT).show();
//                                        }
//                                    }
//
//                                    @Override
//                                    public void onCancelled(@NonNull DatabaseError error) {
//                                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            } else {
//                                mDialog.dismiss();
//                                Toast.makeText(SignIn.this, "User does not exist", Toast.LENGTH_SHORT).show();
//                                num.requestFocus();
//                            }
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
                }

            }
        });

    }

    private boolean isValid() {
        num.setErrorEnabled(false);
        num.setError("");
        voterpass.setErrorEnabled(false);
        voterpass.setError("");

        boolean isValid = false, isValidnum = false, isValidpass = false;
        if(TextUtils.isEmpty(number) || number.length() != 10){
            num.setErrorEnabled(true);
            if(number.isEmpty()) num.setError("Phone number is required");
            else num.setError("Please enter valid phone number");
        }
        else isValidnum = true;

        if(TextUtils.isEmpty(pass)){
            voterpass.setErrorEnabled(true);
            voterpass.setError("Password is required");
        }
        else isValidpass = true;

        isValid = (isValidnum && isValidpass) ? true : false;
        return  isValid;
    }
}
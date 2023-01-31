package com.example.onlinevote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class VotingActivity extends AppCompatActivity {

    CircleImageView img;
    TextView partyName;
    Button vote_btn;
    DatabaseReference reference;
    int Total;
    String  phoneNumber;
    FirebaseFirestore fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);

//        Toast.makeText(this, "Voting Activity class!!", Toast.LENGTH_SHORT).show();


        reference = FirebaseDatabase.getInstance().getReference("User");
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        if(user != null){
            phoneNumber = user.getPhoneNumber();
//            Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        }
        else Toast.makeText(this, "Null Phone Number", Toast.LENGTH_SHORT).show();

        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();

        img = (CircleImageView) findViewById(R.id.profile_img);
        partyName = (TextView) findViewById(R.id.party_name);
        vote_btn = (Button) findViewById(R.id.vote_btn);


        Intent intent = getIntent();
        int Count = intent.getIntExtra("cnt", 0);
        String party = intent.getStringExtra("partyName");
        String url = intent.getStringExtra("image");
        String UID = intent.getStringExtra("ID");

        Glide.with(this).load(url).into(img);
        partyName.setText(party);

        vote_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(VotingActivity.this);
                builder.setTitle("Alert").
                        setMessage("Are you sure?");
                builder.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int id) {
                                HashMap Vote = new HashMap();
                                Vote.put("count", Count+1);
                                FirebaseDatabase.getInstance().getReference("View").child(UID).updateChildren(Vote);
                                FirebaseDatabase.getInstance().getReference("Party")
                                        .child(UID).updateChildren(Vote).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if(task.isSuccessful()){
                                            Toast.makeText(VotingActivity.this, "Thank you for your vote!", Toast.LENGTH_SHORT).show();

                                            reference.child(phoneNumber).child("voted").setValue(1);
                                            HashMap<String, Object> User = new HashMap<>();
                                            User.put("voted", 1);
                                            fb.getInstance().collection("User").document(phoneNumber).update(User);

                                            Intent b = new Intent(VotingActivity.this, VoterHome.class);
                                            b.putExtra("phoneNumber", phoneNumber);
                                            b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                            startActivity(b);
                                            finish();
                                        }
                                        else Toast.makeText(VotingActivity.this, "Something Error", Toast.LENGTH_SHORT).show();
                                    }
                                });
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
}
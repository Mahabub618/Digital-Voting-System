package com.example.onlinevote;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddParty extends AppCompatActivity {

    Button b1;
    EditText e1, e2;
    DatabaseReference db, dataa;
    FirebaseStorage storage;
    StorageReference storageReference, ref;
    ImageButton candidateImg;
    FirebaseAuth mAuth;
    Uri imageuri;
    String RandomUID, partyName, partyNumber;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_party);

        b1 = (Button) findViewById(R.id.addButton);
        e1 = (EditText) findViewById(R.id.inputPartyName);
        e2 = (EditText) findViewById(R.id.inputMarkNo);
        candidateImg = (ImageButton) findViewById(R.id.partyImage);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance().getReference("Party");
        dataa = FirebaseDatabase.getInstance().getReference("View");

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        candidateImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImageFromGallery();
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                partyName = e1.getText().toString().trim();
                partyNumber = e2.getText().toString().trim();

                if(partyName.isEmpty())
                {
                    e1.setError("This field cannot be empty");
                    e1.requestFocus();
                }
                if(partyNumber.isEmpty())
                {
                    e2.setError("This field cannot be empty");
                    e2.requestFocus();
                }

                ProgressDialog mDialog = new ProgressDialog(AddParty.this);
                mDialog.setMessage("Please Waiting...");
                mDialog.show();

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.child(partyNumber).exists())
                        {
                            e2.setError("This party number already exist");
                            e2.requestFocus();
                            mDialog.dismiss();
                        }
                        else if(snapshot.child(partyNumber).exists())
                        {
                            e1.setError("This party name already exist");
                            e1.requestFocus();
                            mDialog.dismiss();
                        }
                        else
                        {
                            uploadImage();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }

    private void uploadImage(){

        if(imageuri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(AddParty.this);
            progressDialog.setTitle("Uploading....");
            progressDialog.show();
            RandomUID = UUID.randomUUID().toString();
            ref = storageReference.child(RandomUID);
            ref.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Voter voter = new Voter(partyName, String.valueOf(uri), partyNumber, 0);

                            db.child(partyNumber).setValue(voter);
                            dataa.child(partyNumber).setValue(voter);
                            Toast.makeText(AddParty.this, "Party added in successfully", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();

                            Intent intent = new Intent(AddParty.this, AdminHome.class);
                            startActivity(intent);

                            e1.setText("");
                            e2.setText("");
                            finish();
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(AddParty.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    double progress = (100.0*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+(int) progress+"%");
                    progressDialog.setCanceledOnTouchOutside(false);
                }
            });
        }
        else
        {
            Toast.makeText(AddParty.this, "Needed a symbol picture", Toast.LENGTH_SHORT).show();
        }
    }

    private void pickImageFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    @SuppressLint("NewApi")
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE){
            ((ImageButton)findViewById(R.id.partyImage)).setImageURI(data.getData());
            imageuri = data.getData();
        }
    }

}
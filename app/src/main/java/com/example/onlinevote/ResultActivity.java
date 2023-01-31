package com.example.onlinevote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ResultActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ResultAdapter resultAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        recyclerView = (RecyclerView) findViewById(R.id.result_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("Party").orderByChild("count"), Model.class)
                        .build();

        resultAdapter = new ResultAdapter(options);
        recyclerView.setAdapter(resultAdapter);
    }

    @Override
    protected void onStart(){
        super.onStart();
        resultAdapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        resultAdapter.stopListening();
    }
}
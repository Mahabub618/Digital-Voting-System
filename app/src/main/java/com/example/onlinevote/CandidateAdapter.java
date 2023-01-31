package com.example.onlinevote;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class CandidateAdapter extends FirebaseRecyclerAdapter<Model, CandidateAdapter.myviewholder> {

    public CandidateAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    public void onBindViewHolder(@NonNull CandidateAdapter.myviewholder holder, int position, @NonNull Model model) {

        Glide.with(holder.img.getContext()).load(model.getImageURL()).into(holder.img);
        holder.partyName.setText(model.getName());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent b = new Intent(view.getContext(), VotingActivity.class);
                b.putExtra("cnt", model.getCount());
                b.putExtra("image", model.getImageURL());
                b.putExtra("partyName", model.getName());
                b.putExtra("ID", model.getRandomUID());

                view.getContext().startActivity(b);
                Activity activity = (Activity) view.getContext();
                activity.finish();

            }
        });
    }

    @NonNull
    @Override
    public CandidateAdapter.myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.candidate_layout, parent, false);
        return  new CandidateAdapter.myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView partyName;
        CardView cardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.profile_img);
            partyName = (TextView) itemView.findViewById(R.id.party_name);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }
}

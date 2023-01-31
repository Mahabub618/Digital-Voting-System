package com.example.onlinevote;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class ResultAdapter extends FirebaseRecyclerAdapter<Model, ResultAdapter.myviewholder> {
    public ResultAdapter(@NonNull FirebaseRecyclerOptions<Model> options) { super(options);}

        @Override
        public void onBindViewHolder(@NonNull ResultAdapter.myviewholder holder, int position, @NonNull Model model) {

            Glide.with(holder.img.getContext()).load(model.getImageURL()).into(holder.img);
            holder.partyName.setText(model.getName());
            holder.cast.setText(String.valueOf(model.getCount()));
        }
    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_layout, parent, false);
        return  new myviewholder(view);
    }
    class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView partyName, cast;
        CardView cardView;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.profile_img);
            partyName = (TextView) itemView.findViewById(R.id.party_name);
            cast = (TextView) itemView.findViewById(R.id.tot_cnt);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
        }
    }

}
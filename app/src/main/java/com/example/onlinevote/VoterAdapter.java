package com.example.onlinevote;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class VoterAdapter extends FirebaseRecyclerAdapter<Model, VoterAdapter.myviewholder> {

    public VoterAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Model model) {

        Glide.with(holder.img.getContext()).load(model.getImageURL()).into(holder.img);
        holder.partyName.setText(model.getName());

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow, parent, false);
        return  new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder{

        CircleImageView img;
        TextView partyName;
        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img = (CircleImageView) itemView.findViewById(R.id.img1);
            partyName = (TextView) itemView.findViewById(R.id.partyname);
        }
    }
}

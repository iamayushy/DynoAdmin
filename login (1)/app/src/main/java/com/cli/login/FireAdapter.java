package com.cli.login;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class FireAdapter extends FirebaseRecyclerAdapter<Post, FireAdapter.PostViewHolder>{


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FireAdapter(@NonNull FirebaseRecyclerOptions<Post> options) {
        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull PostViewHolder holder, int position, @NonNull Post model) {
        Picasso.get().load(model.getImageUrl()).into(holder.imageView);
        holder.TitleText.setText(model.getTitle());
        holder.Description.setText(model.getDescription());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), NewRead.class);
                intent.putExtra("title", model.getTitle());
                intent.putExtra("description", model.getCompleteNews());
                intent.putExtra("url", model.getImageUrl());
                view.getContext().startActivity(intent);
            }
        });

    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.blogpost, parent, false);

        return new PostViewHolder(view);
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView TitleText;
        TextView Description;
        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.newsimage);
            TitleText = itemView.findViewById(R.id.tvtext);
            Description = itemView.findViewById(R.id.tvbody);
        }
    }
}

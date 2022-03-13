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

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class adapter extends RecyclerView.Adapter<adapter.NewsHolder>{

    ArrayList<articles> articlesArrayList;
    private Context context;

    public adapter(ArrayList<articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news, parent, false);
        return new adapter.NewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder holder, int position) {
        articles articles = articlesArrayList.get(position);
        holder.tvtitle.setText(articles.getTitle());
        holder.tvbody.setText(articles.getDescription());
        Picasso.get().load(articles.getUrlToImage()).into(holder.newsimage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MainActivity2.class);
                intent.putExtra("title", articles.getTitle());
                intent.putExtra("image", articles.getUrlToImage());
                intent.putExtra("content", articles.getContent());
                intent.putExtra("url", articles.getUrl());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class NewsHolder extends RecyclerView.ViewHolder {
        private TextView tvtitle;
        private TextView tvbody;
        private ImageView newsimage;
        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            tvtitle = itemView.findViewById(R.id.tvtitle);
            tvbody = itemView.findViewById(R.id.tvbody);
            newsimage = itemView.findViewById(R.id.newsimage);

        }
    }
}


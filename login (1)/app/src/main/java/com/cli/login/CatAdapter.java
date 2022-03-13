package com.cli.login;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class CatAdapter extends RecyclerView.Adapter<CatAdapter.ViewHolder>{

    public ArrayList<category> categoryArrayList;
    private CategoryClick categoryClick;

    public CatAdapter(ArrayList<category> categoryArrayList, CategoryClick categoryClick) {
        this.categoryArrayList = categoryArrayList;
        this.categoryClick = categoryClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.catering, parent, false);
        return new CatAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        category category = categoryArrayList.get(position);
        holder.catext.setText(category.getCatext());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                categoryClick.OnCategoryClick(holder.getAdapterPosition());
            }
        });


    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public interface CategoryClick{
        void OnCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView catext;
        View mView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catext = itemView.findViewById(R.id.catext);
            mView = itemView;
        }
    }
}
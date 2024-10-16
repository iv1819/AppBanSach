package com.example.appbansach;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {

    Context context;
    List<String> genrelist;

    public GenresAdapter(Context context, List<String> gList) {
        this.context = context;
        this.genrelist = gList;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.genres_holder_layout, parent, false);

        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {

        holder.categoryImage.setImageResource(genrelist.get(position).getImageurl());

    }

    @Override
    public int getItemCount() {
        return genrelist.size();
    }

    public  static class GenreViewHolder extends RecyclerView.ViewHolder{

        TextView genreName;


        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
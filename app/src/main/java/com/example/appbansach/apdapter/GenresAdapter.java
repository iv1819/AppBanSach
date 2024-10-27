package com.example.appbansach.apdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbansach.R;
import com.example.appbansach.TrangChu;
import com.example.appbansach.model.Genre;
import com.example.appbansach.model.SanPham;

import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.GenreViewHolder> {
    private Context context;
    private List<Genre> list;

    public GenresAdapter(Context context, List<Genre> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GenreViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.genres_holder_layout, parent, false);
        return new GenreViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GenreViewHolder holder, int position) {
        Genre genre = list.get(position);
        holder.txtGen.setText(genre.getTentheloai());
        // Handle genre click directly here
        holder.itemView.setOnClickListener(v -> {
            // Call the method in TrangChu to filter products
            ((TrangChu) context).filterProductsByGenre(genre.getTentheloai());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class GenreViewHolder extends RecyclerView.ViewHolder {
        TextView txtGen;

        public GenreViewHolder(@NonNull View itemView) {
            super(itemView);
            txtGen = itemView.findViewById(R.id.txtName);
        }
    }
}


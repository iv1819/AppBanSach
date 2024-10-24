package com.example.appbansach.apdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appbansach.R;
import com.example.appbansach.model.SanPham;

import java.util.List;

public class GenresAdapter extends BaseAdapter {
    Context context;
    List<SanPham> list;

    public GenresAdapter(Context context, List<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.genres_holder_layout, viewGroup, false);
        }
        TextView txtGen = view.findViewById(R.id.txtName);
        SanPham sp = list.get(i);
        txtGen.setText(sp.getTheLoai());
        return view;
    }
}

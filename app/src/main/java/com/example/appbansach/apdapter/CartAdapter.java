package com.example.appbansach.apdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbansach.ProductDetails;
import com.example.appbansach.R;
import com.example.appbansach.model.SanPham;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> sanPhamList;

    public CartAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanPhamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.cart_items, viewGroup, false);
        }

        ImageView imgAnhSanPham = view.findViewById(R.id.imgPCart);
        TextView txtTenSanPham = view.findViewById(R.id.txtNameCart);
        TextView txtGiaBan = view.findViewById(R.id.txtPriceCart);

        SanPham sanPham = sanPhamList.get(i);

        byte[] anh = sanPham.getAnhSanPham();
        Bitmap bitmap = getBitmapFromBytes(anh);
        imgAnhSanPham.setImageBitmap(bitmap);
        txtTenSanPham.setText(sanPham.getTenSanPham());
        txtGiaBan.setText(String.format("%s$", sanPham.getGiaBan()));
        // Set onClickListener cho toàn bộ item
        view.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetails.class);

            // Truyền dữ liệu qua Intent
            intent.putExtra("tenSanPham", sanPham.getTenSanPham());
            intent.putExtra("giaBan", sanPham.getGiaBan());
            intent.putExtra("anhSanPham", sanPham.getAnhSanPham()); // truyền mảng byte
            context.startActivity(intent);
        });
        return view;
    }

    // Chuyển Byte thành Bitmap
    public Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

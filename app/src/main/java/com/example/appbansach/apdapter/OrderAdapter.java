package com.example.appbansach.apdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbansach.ProductDetails;
import com.example.appbansach.R;
import com.example.appbansach.model.SanPham;

import java.util.List;

public class OrderAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> sanPhamList;
    private List<Integer> quantityList; // Add a list for quantities

    public OrderAdapter(Context context, List<SanPham> sanPhamList, List<Integer> quantityList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
        this.quantityList = quantityList; // Initialize the quantity list
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
            view = LayoutInflater.from(context).inflate(R.layout.order_items, viewGroup, false);
        }

        ImageView imgAnhSanPham = view.findViewById(R.id.imgOrderItem);
        TextView txtTenSanPham = view.findViewById(R.id.txtNameOrder);
        TextView txtGiaBan = view.findViewById(R.id.txtPriceOrder);
        TextView txtSoLuong = view.findViewById(R.id.txtQtyOrder);
        SanPham sanPham = sanPhamList.get(i);

        byte[] anh = sanPham.getAnhSanPham();
        Bitmap bitmap = getBitmapFromBytes(anh);
        imgAnhSanPham.setImageBitmap(bitmap);
        txtTenSanPham.setText(sanPham.getTenSanPham());
        txtGiaBan.setText(String.format("%s$", sanPham.getGiaBan()));

        // Set the quantity from the quantityList
        if (quantityList != null && i < quantityList.size()) {
            txtSoLuong.setText(String.valueOf(quantityList.get(i))); // Set the quantity in the TextView
        } else {
            txtSoLuong.setText("0"); // Default value if quantityList is not available
        }

        return view;
    }


    // Chuyển Byte thành Bitmap
    public Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

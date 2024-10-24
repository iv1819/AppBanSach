package com.example.appbansach.apdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbansach.R;
import com.example.appbansach.model.SanPham;

import java.util.List;

public class ProductDetailsAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> sanPhamList;

    public ProductDetailsAdapter(Context context, List<SanPham> sanPhamList) {
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
            view = LayoutInflater.from(context).inflate(R.layout.activity_product_details, viewGroup, false);
        }

        ImageView imgAnhSanPham = view.findViewById(R.id.imgPdd);
        TextView txtTenSanPham = view.findViewById(R.id.txtNamePDD);
        TextView txtSoLuong = view.findViewById(R.id.txtSlPDD);
        TextView txtTacGia = view.findViewById(R.id.txtTgPDD);
        TextView txtGiaBan = view.findViewById(R.id.txtPrPDD);

        SanPham sanPham = sanPhamList.get(i);

        byte[] anh = sanPham.getAnhSanPham();
        Bitmap bitmap = getBitmapFromBytes(anh);
        imgAnhSanPham.setImageBitmap(bitmap);
        txtTenSanPham.setText(sanPham.getTenSanPham());
        txtSoLuong.setText(String.format("Số lượng: %s", sanPham.getSoLuong()));
        txtTacGia.setText(sanPham.getTacGia());
        txtGiaBan.setText(String.format("%s$", sanPham.getGiaBan()));

        return view;
    }

    // Chuyển Byte thành Bitmap
    public Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

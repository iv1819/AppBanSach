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

import androidx.recyclerview.widget.RecyclerView;

import com.example.appbansach.ProductDetails;
import com.example.appbansach.R;
import com.example.appbansach.model.SanPham;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private Context context;
    private List<SanPham> sanPhamList;

    public ProductAdapter(Context context, List<SanPham> sanPhamList) {
        this.context = context;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        // Return the number of rows required, which is half the size of the list
        return (int) Math.ceil(sanPhamList.size() / 2.0); // Each row has 2 items
    }

    @Override
    public Object getItem(int position) {
        return null; // Not needed for this use case
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.product_layout, parent, false);
        }

        // First product
        ImageView imgPrd1 = convertView.findViewById(R.id.imgPrd1);
        TextView txtNamePD1 = convertView.findViewById(R.id.txtNamePD1);
        TextView txtPricePD1 = convertView.findViewById(R.id.txtPricePD1);

        // Second product (if exists)
        ImageView imgPrd2 = convertView.findViewById(R.id.imgPrd2);
        TextView txtNamePD2 = convertView.findViewById(R.id.txtNamePD2);
        TextView txtPricePD2 = convertView.findViewById(R.id.txtPricePD2);

        // First product for the current row (always present)
        SanPham product1 = sanPhamList.get(position * 2);
        imgPrd1.setImageBitmap(getBitmapFromBytes(product1.getAnhSanPham()));
        txtNamePD1.setText(product1.getTenSanPham());
        txtPricePD1.setText(String.format("%s$", product1.getGiaBan()));

        // Set click listener for the first product
        convertView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductDetails.class);
            intent.putExtra("selected_product", product1); // Pass the product object
            context.startActivity(intent);
        });

        // Second product for the current row (may not exist)
        if (position * 2 + 1 < sanPhamList.size()) {
            SanPham product2 = sanPhamList.get(position * 2 + 1);
            imgPrd2.setImageBitmap(getBitmapFromBytes(product2.getAnhSanPham()));
            txtNamePD2.setText(product2.getTenSanPham());
            txtPricePD2.setText(String.format("%s$", product2.getGiaBan()));

            // Set click listener for the second product
            imgPrd2.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetails.class);
                intent.putExtra("selected_product", product2); // Pass the product object
                context.startActivity(intent);
            });

            // Make the second product visible
            imgPrd2.setVisibility(View.VISIBLE);
            txtNamePD2.setVisibility(View.VISIBLE);
            txtPricePD2.setVisibility(View.VISIBLE);
        } else {
            // If there’s no second product, hide the second column
            imgPrd2.setVisibility(View.INVISIBLE);
            txtNamePD2.setVisibility(View.INVISIBLE);
            txtPricePD2.setVisibility(View.INVISIBLE);
        }

        return convertView;
    }


    // Convert byte array to bitmap (unchanged)
    private Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}


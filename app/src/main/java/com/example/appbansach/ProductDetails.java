package com.example.appbansach;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ProductDetails extends AppCompatActivity {
    TextView txtTenSanPham, txtGiaBan;
    ImageView imgAnhSanPham, imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_product_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgBack = findViewById(R.id.imgBack);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        String tenSanPham = intent.getStringExtra("tenSanPham");
        String giaBan = intent.getStringExtra("giaBan");
        byte[] anhSanPham = intent.getByteArrayExtra("anhSanPham");

        // Hiển thị dữ liệu
        txtTenSanPham = findViewById(R.id.txtNamePDD);
        txtGiaBan = findViewById(R.id.txtPrPDD);
        imgAnhSanPham = findViewById(R.id.imgPdd);

        txtTenSanPham.setText(tenSanPham);
        txtGiaBan.setText(giaBan + "$");

        // Hiển thị ảnh sản phẩm từ byte[]
        Bitmap bitmap = BitmapFactory.decodeByteArray(anhSanPham, 0, anhSanPham.length);
        imgAnhSanPham.setImageBitmap(bitmap);
        imgBack.setOnClickListener(v -> {
            finish();
        });
    }
}

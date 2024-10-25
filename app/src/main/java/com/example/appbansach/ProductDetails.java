package com.example.appbansach;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbansach.model.GioHang;
import com.example.appbansach.model.SanPham;

public class ProductDetails extends AppCompatActivity {
    ImageView imgBack;
    Button themGioHang;
    GioHang cart;
    SanPham selectedProduct;

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
        themGioHang = findViewById(R.id.btnCart);
        // Get the passed product object
        selectedProduct = (SanPham) getIntent().getSerializableExtra("selected_product");
        cart = MyApplication.getInstance().getGioHang();

        // Populate your views with the product details
        if (selectedProduct != null) {
            ImageView imgAnhSanPham = findViewById(R.id.imgPdd);
            TextView txtTenSanPham = findViewById(R.id.txtNamePDD);
            TextView txtSoLuong = findViewById(R.id.txtSlPDD);
            TextView txtTacGia = findViewById(R.id.txtTgPDD);
            TextView txtGiaBan = findViewById(R.id.txtPrPDD);

            byte[] anh = selectedProduct.getAnhSanPham();
            Bitmap bitmap = getBitmapFromBytes(anh);
            imgAnhSanPham.setImageBitmap(bitmap);
            txtTenSanPham.setText(selectedProduct.getTenSanPham());
            txtSoLuong.setText(String.format("Số lượng: %s", selectedProduct.getSoLuong()));
            txtTacGia.setText(selectedProduct.getTacGia());
            txtGiaBan.setText(String.format("%s$", selectedProduct.getGiaBan()));
        }

        imgBack.setOnClickListener(v -> {
            finish();
        });
        themGioHang.setOnClickListener(v -> {
            if (selectedProduct != null) {
                // Thêm sản phẩm vào giỏ hàng
                cart.addToCart(selectedProduct.getMaSanPham()); // Gọi phương thức thêm sản phẩm
                Toast.makeText(this, "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Convert Byte to Bitmap
    private Bitmap getBitmapFromBytes(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }
}

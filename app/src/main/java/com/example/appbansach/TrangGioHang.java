package com.example.appbansach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbansach.apdapter.CartAdapter;
import com.example.appbansach.model.GioHang;
import com.example.appbansach.model.SanPham;

import java.util.List;

public class TrangGioHang extends AppCompatActivity {
    private ListView listViewCart;
    private CartAdapter cartAdapter;

    ImageView mainNav, userNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gio_hang);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GioHang gioHang = MyApplication.getInstance().getGioHang();
        mainNav = findViewById(R.id.CartToMainNav);
        listViewCart = findViewById(R.id.lvCart);
        List<SanPham> cartItems = gioHang.getCartItems();

        // Set up the adapter
        cartAdapter = new CartAdapter(this, cartItems, gioHang);
        listViewCart.setAdapter(cartAdapter);
        mainNav.setOnClickListener(v -> {
            Intent intent = new Intent(TrangGioHang.this, TrangChu.class);
            startActivity(intent);
            finish();
        });
    }
}
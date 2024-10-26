package com.example.appbansach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbansach.apdapter.CartAdapter;
import com.example.appbansach.model.GioHang;
import com.example.appbansach.model.SanPham;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TrangGioHang extends AppCompatActivity {
    private ListView listViewCart;
    private CartAdapter cartAdapter;
    GioHang gioHang;
    Button btnBuy;
    ImageView mainNav, userNav;
    Database db = new Database(this);
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
        db.createDatabase();
        gioHang = MyApplication.getInstance().getGioHang();
        mainNav = findViewById(R.id.CartToMainNav);
        listViewCart = findViewById(R.id.lvCart);
        List<SanPham> cartItems = gioHang.getCartItems();
        btnBuy = findViewById(R.id.btnBuy);
        // Set up the adapter
        cartAdapter = new CartAdapter(this, cartItems, gioHang);
        listViewCart.setAdapter(cartAdapter);
        mainNav.setOnClickListener(v -> {
            Intent intent = new Intent(TrangGioHang.this, TrangChu.class);
            startActivity(intent);
            finish();
        });

        btnBuy.setOnClickListener(v -> {
            Intent intent = new Intent(TrangGioHang.this, TrangThanhToan.class);
            intent.putIntegerArrayListExtra("selectedItems", new ArrayList<>(cartAdapter.getSelectedItems()));
            intent.putIntegerArrayListExtra("selectedItemsQty", new ArrayList<>(cartAdapter.getSelectedItemsQty()));
            startActivity(intent);
            finish();
        });
    }
}
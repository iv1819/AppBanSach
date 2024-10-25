package com.example.appbansach;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.appbansach.apdapter.BannerAdapter;
import com.example.appbansach.model.DangKyUser;
import com.example.appbansach.model.DangNhapQLy;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

public class MainActivity extends AppCompatActivity {

    Button btnDangNhap;
    Button btnDangKy;
    Button btnDangNhapQLy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Khởi tạo nút đăng nhập
        btnDangNhap = findViewById(R.id.btnDangNhap);
        btnDangNhap.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DangNhapUser.class);
            startActivity(intent);
        });
        btnDangKy = findViewById(R.id.btnDangKy);
        btnDangKy.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DangKyUser.class);
            startActivity(intent);
        });
        String customerId = "KH01";
        MyApplication.getInstance().initializeGioHang(customerId);

        btnDangNhapQLy = findViewById(R.id.btnDangNhapQly);
        btnDangNhapQLy.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DangNhapQLy.class);
            startActivity(intent);
        });
    }


}

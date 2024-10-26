package com.example.appbansach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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


        btnDangNhapQLy = findViewById(R.id.btnDangNhapQly);
        btnDangNhapQLy.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, DangNhapQLy.class);
            startActivity(intent);
        });
    }


}

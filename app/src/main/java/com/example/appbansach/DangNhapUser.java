package com.example.appbansach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbansach.model.TaiKhoan;

public class DangNhapUser extends AppCompatActivity {

    TaiKhoan taiKhoan;
    EditText edtName; // Khai báo trường nhập tên đăng nhập
    EditText edtPassword; // Khai báo trường nhập mật khẩu


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        taiKhoan = new TaiKhoan(this);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
    }
    private void DangNhap() {
        // Lấy thông tin từ các trường nhập liệu
        String sTaiKhoan = edtName.getText().toString().trim(); // Lấy tên đăng nhập
        String sMatKhau = edtPassword.getText().toString().trim(); // Lấy mật khẩu

        // Kiểm tra thông tin đăng nhập
        boolean kiemTra = taiKhoan.kiemtraDangNhap(sTaiKhoan, sMatKhau);

        if (kiemTra) {
            Toast.makeText(this, "Đăng nhập thành công!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DangNhapUser.this, TrangChu.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Đăng nhập thất bại! Vui lòng kiểm tra lại thông tin.", Toast.LENGTH_SHORT).show();
        }
    }
}
package com.example.appbansach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbansach.model.TaiKhoan;

public class DangNhapQLy extends AppCompatActivity {

    TaiKhoan taiKhoan;
    EditText edtName;
    EditText edtPassword;
    Button btnLogIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap_qly);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        taiKhoan = new TaiKhoan(this);
        edtName = findViewById(R.id.edtName);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogIn = findViewById(R.id.btnLogIn);

        Button btnLogIn = findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(view -> DangNhap());
    }
    private void DangNhap() {
        String sTaiKhoan = edtName.getText().toString().trim();
        String sMatKhau = edtPassword.getText().toString().trim();

        // Kiểm tra thông tin đăng nhập
        boolean kiemTra = taiKhoan.kiemtraDangNhapQLy(sTaiKhoan, sMatKhau);

        if (kiemTra) {
            Toast.makeText(this, "Log in success!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(DangNhapQLy.this, TrangChu.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Error.", Toast.LENGTH_SHORT).show();
        }
    }
}
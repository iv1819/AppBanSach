package com.example.appbansach;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class DangKyUser extends AppCompatActivity {

    Database database = new Database(this);;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_ky_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database.createDatabase(); //tao csdl

        EditText edtName = findViewById(R.id.edtName);
        EditText edtPhone = findViewById(R.id.edtPhone);
        EditText edtAddress = findViewById(R.id.edtAddress);
        EditText editPassWord = findViewById(R.id.edtPassWord);
        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tenKhachHang = edtName.getText().toString();
                String soDienThoai = edtPhone.getText().toString();
                String diaChi = edtAddress.getText().toString();
                String matKhau = editPassWord.getText().toString();
                if (!tenKhachHang.isEmpty() && !soDienThoai.isEmpty() && !diaChi.isEmpty() && !matKhau.isEmpty()) {
                    String maKhachHang = database.addCustomer(tenKhachHang, soDienThoai, diaChi); // Lưu mã khách hàng

                    if (maKhachHang != null) {
                        boolean accountSuccess = database.addtaikhoanuser(tenKhachHang, matKhau, maKhachHang); // Thêm tài khoản
                        if (accountSuccess) {
                            Toast.makeText(DangKyUser.this, "Login Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(DangKyUser.this, "Failed!", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(DangKyUser.this, "Failed!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(DangKyUser.this, "Please enter all information!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
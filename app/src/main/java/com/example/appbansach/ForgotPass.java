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

public class ForgotPass extends AppCompatActivity {

    private EditText edtOld, edtNew;
    private Button buttonUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass); // Đảm bảo tên này khớp với tên file XML của bạn

        edtOld = findViewById(R.id.edtOld);
        edtNew = findViewById(R.id.edtNew);
        buttonUpdate = findViewById(R.id.buttonUpDate);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePassword();
            }
        });
    }
    private void updatePassword() {
        String oldPassword = edtOld.getText().toString().trim();
        String newPassword = edtNew.getText().toString().trim();

        // Kiểm tra thông tin đầu vào
        if (oldPassword.isEmpty() || newPassword.isEmpty()) {
            Toast.makeText(this, "Please enter all information!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu cũ
        if (!isOldPasswordCorrect(oldPassword)) {
            Toast.makeText(this, "Something wrong!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật mật khẩu mới vào cơ sở dữ liệu
        updatePasswordInDatabase(newPassword);
    }

    private boolean isOldPasswordCorrect(String oldPassword) {
        return true;
    }

    private void updatePasswordInDatabase(String newPassword) {
        // Cập nhật mật khẩu mới vào bảng taikhoankhachhang.
        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
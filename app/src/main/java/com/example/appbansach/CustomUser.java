package com.example.appbansach;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class CustomUser extends AppCompatActivity {

    private TextView tvThongTin;
    private EditText edtNameUser, edtPassWordUser, edtPhoneUser, edtAddressUser;
    private Button buttonUpdate, buttonDelete; // Thêm buttonDelete

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_custom_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tvThongTin = findViewById(R.id.tvThongTin);
        edtNameUser = findViewById(R.id.edtNameUser);
        edtPassWordUser = findViewById(R.id.edtPassWordUser);
        edtPhoneUser = findViewById(R.id.edtPhoneUser);
        edtAddressUser = findViewById(R.id.edtAddressUser);
        buttonUpdate = findViewById(R.id.buttonUpdate); // Sử dụng buttonAdd cho chức năng cập nhật
        buttonDelete = findViewById(R.id.buttonDelete); // Khởi tạo buttonDelete

        // Lấy customerId từ MyApplication
        String customerId = MyApplication.getInstance().getCustomerID();

        // Gọi phương thức để lấy thông tin người dùng
        Database database = new Database(this);
        String[] userInfo = database.getUserByCustomerId(customerId);

        // Điền thông tin vào các EditText
        if (userInfo != null) {
            edtPhoneUser.setText(userInfo[0]); // SĐT
            edtAddressUser.setText(userInfo[1]); // Địa chỉ
            edtNameUser.setText(userInfo[2]); // Tài khoản
            edtPassWordUser.setText(userInfo[3]); // Mật khẩu
        }

        buttonUpdate.setOnClickListener(v -> updateUser(customerId));
        buttonDelete.setOnClickListener(v -> deleteUser(customerId));
    }

    private void updateUser(String customerId) {
        String name = edtNameUser.getText().toString();
        String password = edtPassWordUser.getText().toString();
        String phone = edtPhoneUser.getText().toString();
        String address = edtAddressUser.getText().toString();

        // Kiểm tra các trường thông tin không trống
        if (name.isEmpty() || password.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Please enter all information!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Cập nhật thông tin người dùng trong cơ sở dữ liệu
        Database database = new Database(this);
        database.updateUser(customerId, phone, address, name, password);

        // Thông báo thành công
        Toast.makeText(this, "Updated!", Toast.LENGTH_SHORT).show();
    }

    private void deleteUser(String customerId) {
        // Xóa tài khoản trong cơ sở dữ liệu
        Database database = new Database(this);
        database.deleteUser(customerId);

        // Thông báo thành công
        Toast.makeText(this, "Deleted!", Toast.LENGTH_SHORT).show();

        // Chuyển hướng về MainActivity
        Intent intent = new Intent(CustomUser.this, MainActivity.class);
        startActivity(intent);
        finish(); // Kết thúc hoạt động hiện tại
    }
}


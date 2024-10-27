package com.example.appbansach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbansach.apdapter.OrderAdapter;
import com.example.appbansach.model.GioHang;
import com.example.appbansach.model.SanPham;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class TrangThanhToan extends AppCompatActivity {
    Button btnPay;
    private ListView lvOrder;
    private OrderAdapter orderAdapter; // Use your custom adapter
    private Database db;
    private List<SanPham> sanPhamList; // List to hold the products to be displayed
    private TextView txtTotalPrice; // TextView to display total price
    EditText txtName, txtAddress, txtPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_thanh_toan);

        // Set up window insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtName = findViewById(R.id.txtNamePayment);
        txtAddress = findViewById(R.id.txtAddressPaymant);
        txtPhone = findViewById(R.id.txtPhonePayment);
        lvOrder = findViewById(R.id.lvOrder);
        db = new Database(this); // Initialize your database here
        sanPhamList = new ArrayList<>(); // Initialize the product list

        // Get the selected items passed from TrangGioHang
        List<Integer> selectedItems = getIntent().getIntegerArrayListExtra("selectedItems");
        List<Integer> selectedItemsQty = getIntent().getIntegerArrayListExtra("selectedItemsQty");

        // Fetch product details based on selectedItems
        if (selectedItems != null) {
            for (int productId : selectedItems) {
                SanPham product = getProductById(productId); // Fetch product by ID
                if (product != null) {
                    sanPhamList.add(product); // Add the product to the list
                }
            }
        }
        String customerId = MyApplication.getInstance().getGioHang().getCustomerId();

        String[] userInfo = db.getUserByCustomerId(customerId);

        // Điền thông tin vào các EditText
        if (userInfo != null) {
            txtPhone.setText(userInfo[0]); // SĐT
            txtAddress.setText(userInfo[1]); // Địa chỉ
            txtName.setText(userInfo[2]); // Tài khoản
        }
        // Initialize the adapter with the fetched products and quantities
        orderAdapter = new OrderAdapter(this, sanPhamList, selectedItemsQty);
        lvOrder.setAdapter(orderAdapter);
        int totalPrice = calculateTotalPrice(selectedItemsQty);
        TextView txtTotalPrice = findViewById(R.id.txtTotalPrice);
        txtTotalPrice.setText(totalPrice+ "$");
        btnPay = findViewById(R.id.btnPay);
        btnPay.setOnClickListener(v -> {
            GioHang gioHang = new GioHang(db, MyApplication.getInstance().getGioHang().getCustomerId());

            String maDonHang = generateOrderId();
            String orderDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String status = "Chờ duyệt";
            db.insertDonHang(maDonHang, customerId, orderDate, status);

            if (selectedItems != null && selectedItemsQty != null && selectedItems.size() == selectedItemsQty.size()) {
                for (int i = 0; i < selectedItems.size(); i++) {
                    int productId = selectedItems.get(i);
                    int quantity = selectedItemsQty.get(i);
                    db.insertIntoChiTietDonHang(maDonHang, productId, quantity);
                    gioHang.removeFromCart(productId);
                }
            } else {
                Toast.makeText(this, "Error: Items and quantities do not match!", Toast.LENGTH_SHORT).show();
            }

            Toast.makeText(this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
            selectedItems.clear();
            selectedItemsQty.clear();
            Intent intent = new Intent(TrangThanhToan.this, TrangChu.class);
            startActivity(intent);
            finish();

        });
    }

    // Method to retrieve a product by its ID
    private SanPham getProductById(int productId) {
        SQLiteDatabase database = db.getReadableDatabase();
        SanPham product = null;
        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT maSanPham, tenSanPham, anhSanPham, maNhaCungCap, maDanhMuc, tacGia, soLuong, giaBan, maTheLoai FROM SanPham WHERE maSanPham = ?", new String[]{String.valueOf(productId)});

            if (cursor != null && cursor.moveToFirst()) {
                int maSanPham = cursor.getInt(0);
                String tenSanPham = cursor.getString(1);
                byte[] anhSanPham = cursor.getBlob(2);
                String maNhaCungCap = cursor.getString(3);
                String maDanhMuc = cursor.getString(4);
                String tacGia = cursor.getString(5);
                int soLuong = cursor.getInt(6);
                int giaBan = cursor.getInt(7);
                String maTheLoai = cursor.getString(8);

                product = new SanPham(maSanPham, tenSanPham, anhSanPham, maNhaCungCap, maDanhMuc, tacGia, soLuong, giaBan, maTheLoai);
            }
        } catch (Exception e) {
            Log.e("TrangThanhToan", "Error retrieving product", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close(); // Close database after use
        }

        return product;
    }
    // Method to calculate total price
    private int calculateTotalPrice(List<Integer> selectedItemsQty) {
        int totalPrice = 0;

        for (int i = 0; i < sanPhamList.size(); i++) {
            SanPham product = sanPhamList.get(i);
            int quantity = selectedItemsQty.get(i);
            totalPrice += product.getGiaBan() * quantity;
        }

        return totalPrice;
    }


    private String generateOrderId() {
        int randomNum = new Random().nextInt(900000) + 100000; // Generates a 6-digit number
        return "DH" + randomNum;
    }
}

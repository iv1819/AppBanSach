package com.example.appbansach;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.appbansach.Database;
import com.example.appbansach.R;
import com.example.appbansach.apdapter.ProductAdapter;
import com.example.appbansach.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class TimKiem extends AppCompatActivity {

    private EditText edtTimKiem;
    private ListView listView;
    private ProductAdapter adapter;
    private Database database;
    private List<SanPham> sanPhamList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);

        // Initialize views and database
        edtTimKiem = findViewById(R.id.edtTimKiem);
        listView = findViewById(R.id.listView);
        database = new Database(this);

        // Initialize product list and adapter
        sanPhamList = new ArrayList<>();
        adapter = new ProductAdapter(this, sanPhamList);
        listView.setAdapter(adapter);

        // Load all products initially, similar to `TrangChu`
        loadSanPhamList("");

        // Set up text change listener to filter products
        edtTimKiem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadSanPhamList(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Search button to perform search based on input text
        ImageView imageViewSearch = findViewById(R.id.imageView3);
        imageViewSearch.setOnClickListener(v -> {
            String query = edtTimKiem.getText().toString();
            loadSanPhamList(query);
        });
    }

    private void loadSanPhamList(String searchTerm) {
        sanPhamList.clear();
        new Thread(() -> {
            SQLiteDatabase sqLiteDatabase = database.openDatabase();
            Cursor cursor = null;

            try {
                String query = "SELECT maSanPham, tenSanPham, anhSanPham, tenNhaCungCap, tenDanhMuc, tacGia, soLuong, giaBan, tenTheLoai FROM SanPham "
                        + "JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap "
                        + "JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc "
                        + "JOIN TheLoai ON SanPham.maTheLoai = TheLoai.maTheLoai "
                        + "WHERE tenSanPham LIKE ?";
                cursor = sqLiteDatabase.rawQuery(query, new String[]{"%" + searchTerm + "%"});

                if (cursor.moveToFirst()) {
                    do {
                        int maSanPham = cursor.getInt(0);
                        String tenSanPham = cursor.getString(1);
                        byte[] anhSanPham = cursor.getBlob(2);
                        String tenNhaCungCap = cursor.getString(3);
                        String tenDanhMuc = cursor.getString(4);
                        String tenTacGia = cursor.getString(5);
                        int soLuong = cursor.getInt(6);
                        int giaBan = cursor.getInt(7);
                        String tenTheLoai = cursor.getString(8);

                        sanPhamList.add(new SanPham(maSanPham, tenSanPham, anhSanPham, tenNhaCungCap, tenDanhMuc, tenTacGia, soLuong, giaBan, tenTheLoai));
                    } while (cursor.moveToNext());
                }
            } finally {
                if (cursor != null) cursor.close();
                database.closeDatabase();
            }

            // Update adapter on the UI thread
            runOnUiThread(() -> adapter.notifyDataSetChanged());
        }).start();
    }
}

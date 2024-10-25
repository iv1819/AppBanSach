package com.example.appbansach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbansach.apdapter.GenresAdapter;
import com.example.appbansach.apdapter.ProductAdapter;
import com.example.appbansach.model.Genre;
import com.example.appbansach.model.GioHang;
import com.example.appbansach.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity {
    Database db = new Database(this);
    ListView lvSp;
    RecyclerView recyclerView;
    ProductAdapter adapter;
    GenresAdapter genresAdapter;
    List<SanPham> sanPhamList;
    List<Genre> theloaiList;
    ImageView cartna, userna, orderna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        cartna = findViewById(R.id.cartNav);
        db.createDatabase();
        lvSp = findViewById(R.id.lvRcd);
        recyclerView = findViewById(R.id.recyclerViewGenres);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        theloaiList = new ArrayList<>();
        sanPhamList = new ArrayList<>();
        loadSanPhamList();
        loadTheLoaiList();
        cartna.setOnClickListener(view -> {
            Intent intent = new Intent(TrangChu.this, GioHang.class);
            startActivity(intent);
        });
    }
    private void loadTheLoaiList() {
        theloaiList.clear();
        SQLiteDatabase sqLiteDatabase = db.openDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("SELECT maTheLoai, tenTheLoai\n" +
                "FROM TheLoai\n", null);
        if(cursor.moveToFirst()) {
            do {
                String matheloai = cursor.getString(0);
                String tentheloai = cursor.getString(1);
                theloaiList.add(new Genre(matheloai, tentheloai));
            } while(cursor.moveToNext());
        }
        genresAdapter = new GenresAdapter(this, theloaiList);
        recyclerView.setAdapter(genresAdapter);
    }
    private void loadSanPhamList() {
        sanPhamList.clear();
        SQLiteDatabase sqLiteDatabase = db.openDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("SELECT maSanPham, tenSanPham, anhSanPham, tenNhaCungCap, tenDanhMuc, tacGia, soLuong, giaBan, tenTheLoai\n" +
                "FROM SanPham\n" +
                "JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap\n" +
                "JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc\n" +
                "JOIN TheLoai ON SanPham.maTheLoai = TheLoai.maTheLoai", null);
        int rowCount = cursor.getCount();
        Log.d("Database", "Số lượng sản phẩm: " + rowCount);
        if(cursor.moveToFirst()) {
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
            } while(cursor.moveToNext());
        }
        db.closeDatabase();
        adapter = new ProductAdapter(this, sanPhamList);
        lvSp.setAdapter(adapter);
    }
}
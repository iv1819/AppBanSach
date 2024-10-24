package com.example.appbansach;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.appbansach.apdapter.GenresAdapter;
import com.example.appbansach.apdapter.ProductAdapter;
import com.example.appbansach.model.SanPham;

import java.util.ArrayList;
import java.util.List;

public class TrangChu extends AppCompatActivity {
    Database db = new Database(this);
    ListView lvSp, lvGenre;
    ProductAdapter adapter;
    GenresAdapter genresAdapter;
    List<SanPham> sanPhamList;
    List<SanPham> theloaiList;
    int sanPhamDuocChon = -1;
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

        db.createDatabase();
        lvSp = findViewById(R.id.lvRcd);
        lvGenre = findViewById(R.id.lvGenre);
        theloaiList = new ArrayList<>();
        sanPhamList = new ArrayList<>();
        loadSanPhamList();
        loadTheLoaiList();
    }
    private void loadTheLoaiList() {
        theloaiList.clear();
        genresAdapter = new GenresAdapter(this, sanPhamList);
        lvGenre.setAdapter(genresAdapter);
    }
    private void loadSanPhamList() {
        sanPhamList.clear();
        SQLiteDatabase sqLiteDatabase = db.openDatabase();
        Cursor cursor =  sqLiteDatabase.rawQuery("SELECT maSanPham, tenSanPham, anhSanPham, tenNhaCungCap, tenDanhMuc, tacGia, soLuong, giaBan, tenTheLoai\n" +
                "FROM SanPham\n" +
                "JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap\n" +
                "JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc\n" +
                "JOIN TheLoai ON SanPham.maTheLoai = TheLoai.maTheLoai", null);
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
        adapter = new ProductAdapter(this, sanPhamList);
        lvSp.setAdapter(adapter);
    }
}
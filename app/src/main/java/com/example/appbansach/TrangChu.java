package com.example.appbansach;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
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
import androidx.viewpager2.widget.ViewPager2;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.appbansach.apdapter.BannerAdapter;
import com.example.appbansach.apdapter.GenresAdapter;
import com.example.appbansach.apdapter.ProductAdapter;
import com.example.appbansach.model.Genre;
import com.example.appbansach.model.GioHang;
import com.example.appbansach.model.SanPham;
import com.tbuonomo.viewpagerdotsindicator.DotsIndicator;

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
    ImageView cartna;
    ArrayList<SlideModel> imageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_trang_chu);

        // Setup views
        cartna = findViewById(R.id.cartNav);
        db.createDatabase();
        lvSp = findViewById(R.id.lvRcd);
        recyclerView = findViewById(R.id.recyclerViewGenres);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Initialize ImageSlider
        initImageSlider();

        // Load product and genre lists
        theloaiList = new ArrayList<>();
        sanPhamList = new ArrayList<>();
        loadData();

        // Setup click listener for cart navigation
        cartna.setOnClickListener(view -> {
            Intent intent = new Intent(TrangChu.this, TrangGioHang.class);
            startActivity(intent);
            finish();
        });
    }

    private void initImageSlider() {
        // Use the class member imageList
        imageList.add(new SlideModel(R.drawable.banner1, "Description 1", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner2, "Description 2", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner3, "Description 3", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel(R.drawable.banner4, "Description 4", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = findViewById(R.id.imageSlider);
        imageSlider.setImageList(imageList);
        imageSlider.startSliding(3000); // with new period
    }

    private void loadData() {
        new Thread(() -> {
            loadTheLoaiList();
            loadSanPhamList();
        }).start(); // Run in background thread
    }

    private void loadTheLoaiList() {
        theloaiList.clear();
        SQLiteDatabase sqLiteDatabase = db.openDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT maTheLoai, tenTheLoai FROM TheLoai", null);
            if (cursor.moveToFirst()) {
                do {
                    String matheloai = cursor.getString(0);
                    String tentheloai = cursor.getString(1);
                    theloaiList.add(new Genre(matheloai, tentheloai));
                } while (cursor.moveToNext());
            }
        } finally {
            if (cursor != null) cursor.close(); // Always close the cursor
        }
        runOnUiThread(() -> {
            genresAdapter = new GenresAdapter(this, theloaiList);
            recyclerView.setAdapter(genresAdapter);
        });
    }

    private void loadSanPhamList() {
        sanPhamList.clear();
        SQLiteDatabase sqLiteDatabase = db.openDatabase();
        Cursor cursor = null;
        try {
            cursor = sqLiteDatabase.rawQuery("SELECT maSanPham, tenSanPham, anhSanPham, tenNhaCungCap, tenDanhMuc, tacGia, soLuong, giaBan, tenTheLoai FROM SanPham JOIN NhaCungCap ON SanPham.maNhaCungCap = NhaCungCap.maNhaCungCap JOIN DanhMuc ON SanPham.maDanhMuc = DanhMuc.maDanhMuc JOIN TheLoai ON SanPham.maTheLoai = TheLoai.maTheLoai", null);
            int rowCount = cursor.getCount();
            Log.d("Database", "Số lượng sản phẩm: " + rowCount);
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
            if (cursor != null) cursor.close(); // Always close the cursor
            db.closeDatabase();
        }
        runOnUiThread(() -> {
            adapter = new ProductAdapter(this, sanPhamList);
            lvSp.setAdapter(adapter);
        });
    }
}

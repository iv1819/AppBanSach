package com.example.appbansach;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "ql_bansach";
    private static final int DATABASE_VERSION = 1;
    public DbManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tbl_tacgia" +
                " (MaTG text primary key, " +
                "TenTG text not null);");
        db.execSQL("create table tbl_danhmuc" +
                " (MaDM text primary key, " +
                "TenDM text not null);");
        db.execSQL("create table tbl_nhacungcap" +
                " (MaNCC text primary key, " +
                "TenNCC text not null,"+"Sdt text not null, "+ "DiaChi text,"+ "Email text);");
        db.execSQL("create table tbl_khachhang" +
                " (MaKH text primary key, " +
                "TenKH text not null,"+"Sdt text not null,"+ "DiaChi text not null);");
        db.execSQL("create table tbl_donhang" +
                " (SoDH text primary key, " +
                "MaKH text not null,"+"NgayDat text not null,"+ "TrangThaiDonHang text not null,"+ "FOREIGN KEY(MaKH) REFERENCES tbl_khachhang(MaKH) ON DELETE CASCADE);");
        db.execSQL("create table tbl_sanpham" +
                " (MaSP text primary key, " +
                "TenSP text not null,"+"MaNCC text not null,"+"MaDM text not null,"+"NamXuatBan text not null,"+"MaTG text not null,"+"SoLuong int not null,"+"Gia int not null,"+"TheLoai text not null,"+"FOREIGN KEY(MaDM) REFERENCES tbl_danhmuc(MaDM) ON DELETE CASCADE,"+"FOREIGN KEY(MaNCC) REFERENCES tbl_nhacungcap(MaNCC) ON DELETE CASCADE,"+"FOREIGN KEY(MaTG) REFERENCES tbl_tacgia(MaTG) ON DELETE CASCADE);");
        db.execSQL("create table tbl_chitietdonhang" +
                " (ID int primary key autoincrement,"+"MaSP text, " +
                "SoLuong int not null,"+"DonGia int not null," + "SoDH text,"+"GhiChu text,"+"FOREIGN KEY(SoDH) REFERENCES tbl_donhang(SoDH) ON DELETE CASCADE,"+"FOREIGN KEY(MaSP) REFERENCES tbl_sanpham(MaSP) ON DELETE CASCADE);");
        db.execSQL("create table tbl_tkkhachhang" +
                " (TaiKhoan text primary key, " +
                "MatKhau text not null,"+"MaKH text not null,"+"FOREIGN KEY(MaKH) REFERENCES tbl_khachhang(MaKH) ON DELETE CASCADE);");

        db.execSQL("create table tbl_giohang" +
                " (ID int primary key autoincrement, " +
                "MaSP text,"+"FOREIGN KEY(MaSP) REFERENCES tbl_sanpham(MaSP) ON DELETE CASCADE);");
        db.execSQL("create table tbl_tkquanli" +
                " (TaiKhoan text primary key, " +
                "MatKhau text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbl_tacgia");
        onCreate(db);
    }
}

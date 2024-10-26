package com.example.appbansach.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbansach.Database;

public class TaiKhoan {
    private Database database;

    public TaiKhoan(Context context){
        database = new Database(context);
        database.createDatabase();
    }
    public String kiemtraDangNhap(String taiKhoan, String matKhau){
        SQLiteDatabase db = database.openDatabase();
        String maKhachHang = null;

        String query = "SELECT maKhachHang FROM TaiKhoanKhachHang WHERE taiKhoan = ? AND matKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{taiKhoan, matKhau});
        if (cursor.moveToFirst()) {
            maKhachHang = cursor.getString(0);
        }
        cursor.close();
        database.close();

        return maKhachHang;
    }

    public boolean kiemtraDangNhapQLy(String taiKhoan, String matKhau){
        SQLiteDatabase db = database.openDatabase();
        String query = "SELECT * FROM TaiKhoanQuanLy WHERE taiKhoan = ? AND matKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{taiKhoan, matKhau});
        if(cursor.getCount() != 0 ){
            return true;
        }else{
            return false;
        }
    }

}

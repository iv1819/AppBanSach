package com.example.appbansach.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.appbansach.Database;

public class KhachHang {
    private Database database;

    public KhachHang(Context context){
        database = new Database(context);
        database.createDatabase();
    }

    // Thêm phương thức để kiểm tra sự tồn tại của khách hàng
    public boolean kiemTraKhachHang(int maKhachHang) {
        SQLiteDatabase db = database.openDatabase();
        String query = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(maKhachHang)});
        boolean exists = cursor.getCount() > 0;
        cursor.close();// Đừng quên đóng con trỏ
        return exists;
    }



}

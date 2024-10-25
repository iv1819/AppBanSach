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
    public boolean kiemtraDangNhap(String taiKhoan, String matKhau){
        SQLiteDatabase db = database.openDatabase();
        String query = "SELECT * FROM TaiKhoanKhachHang WHERE taiKhoan = ? AND matKhau = ?";
        Cursor cursor = db.rawQuery(query, new String[]{taiKhoan, matKhau});
        if(cursor.getCount() != 0 ){
            return true;
        }else{
            return false;
        }
    }
}

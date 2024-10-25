package com.example.appbansach.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.appbansach.Database;

import java.util.ArrayList;
import java.util.List;

public class GioHang {
    private Database db; // Your database class
    private String customerId; // Store the logged-in customer's ID

    public GioHang(Database db, String customerId) {
        this.db = db;
        this.customerId = customerId;
    }

    // Add product to cart
    public void addToCart(int productId) {
        SQLiteDatabase database = db.openDatabase();
        ContentValues values = new ContentValues();
        values.put("maSanPham", productId);
        values.put("maKhachHang", customerId);
        long result = database.insert("GioHang", null, values);
        if (result == -1) {
            Log.e("GioHang", "Thêm sản phẩm thất bại");
        } else {
            Log.d("GioHang", "Thêm sản phẩm thành công với mã sản phẩm: " + productId + " ma khách hàng: " + customerId);
        }
        database.close();
    }

    // Remove product from cart
    public void removeFromCart(int productId) {
        SQLiteDatabase database = db.openDatabase();
        // Chuyển đổi productId thành String
        int rowsDeleted = database.delete("GioHang", "maKhachHang = ? AND maSanPham = ?", new String[]{customerId, String.valueOf(productId)});
        if (rowsDeleted > 0) {
            Log.d("GioHang", "Xóa sản phẩm thành công với mã sản phẩm: " + productId);
        } else {
            Log.e("GioHang", "Không tìm thấy sản phẩm để xóa với mã sản phẩm: " + productId);
        }
        database.close();
    }


    public List<SanPham> getCartItems() {
        SQLiteDatabase database = db.getReadableDatabase();
        List<SanPham> cartItems = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = database.rawQuery("SELECT p.maSanPham, p.tenSanPham, p.anhSanPham, nc.tenNhaCungCap, dmuc.tenDanhMuc, p.tacGia, p.soLuong, p.giaBan, tl.tenTheLoai " +
                    "FROM GioHang ci " +
                    "JOIN SanPham p ON ci.maSanPham = p.maSanPham " +
                    "JOIN NhaCungCap nc ON p.maNhaCungCap = nc.maNhaCungCap " +
                    "JOIN DanhMuc dmuc ON p.maDanhMuc = dmuc.maDanhMuc " +
                    "JOIN TheLoai tl ON p.maTheLoai = tl.maTheLoai " +
                    "WHERE ci.maKhachHang = ?", new String[]{customerId});

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    int maSanPham = cursor.getInt(0);
                    String tenSanPham = cursor.getString(1);
                    byte[] anhSanPham = cursor.getBlob(2);
                    String tenNhaCungCap = cursor.getString(3);
                    String tenDanhMuc = cursor.getString(4);
                    String tenTacGia = cursor.getString(5);
                    int soLuong = cursor.getInt(6);
                    int giaBan = cursor.getInt(7);
                    String tenTheLoai = cursor.getString(8);

                    cartItems.add(new SanPham(maSanPham, tenSanPham, anhSanPham, tenNhaCungCap, tenDanhMuc, tenTacGia, soLuong, giaBan, tenTheLoai));
                }
            }
        } catch (Exception e) {
            Log.e("GioHang", "Error retrieving cart items", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            database.close(); // Close database after use
        }

        return cartItems;
    }
}



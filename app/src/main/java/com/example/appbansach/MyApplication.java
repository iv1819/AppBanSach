package com.example.appbansach;

import android.app.Application;

import com.example.appbansach.model.GioHang;

public class MyApplication extends Application {
    private static MyApplication instance;
    private GioHang gioHang;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    public GioHang getGioHang() {
        return gioHang;
    }

    // Phương thức để thiết lập giỏ hàng
    public void initializeGioHang(String customerId) {
        if (gioHang == null) {
            gioHang = new GioHang(new Database(this), customerId);
        }
    }

    public void setCustomerId(String customerId) {
        // Khi người dùng mới đăng nhập, đặt ID mới và tạo giỏ hàng mới
        gioHang = new GioHang(new Database(this), customerId);
    }
}

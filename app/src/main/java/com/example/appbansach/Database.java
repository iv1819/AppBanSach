package com.example.appbansach;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.appbansach.model.SanPham;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "AppBanSach.db"; // Chỉ cần tên tệp
    private static final int DATABASE_VERSION = 1;
    private final Context context;
    private SQLiteDatabase database;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Phương thức này sẽ lấy đường dẫn chính xác tới tệp cơ sở dữ liệu
    private String getDatabasePath() {
        return context.getDatabasePath(DATABASE_NAME).getPath();
    }

    public void createDatabase(){
        boolean dbExist = checkDatabase();

        if (!dbExist) {
            this.getWritableDatabase();
            try {
                copyDatabase(); // Sao chép cơ sở dữ liệu từ assets
                Log.d("Database", "Cơ sở dữ liệu đã được sao chép thành công.");
            } catch (Exception e) {
                Log.e("Database", "Lỗi khi sao chép cơ sở dữ liệu: " + e.getMessage());
                throw new Error("Error copying database");
            }
        } else {
            Log.d("Database", "Cơ sở dữ liệu đã tồn tại.");
        }
    }

    // Kiểm tra xem tệp cơ sở dữ liệu đã tồn tại hay chưa
    private boolean checkDatabase() {
        File dbFile = new File(getDatabasePath());
        return dbFile.exists();
    }

    // Sao chép tệp cơ sở dữ liệu từ thư mục assets vào thư mục cơ sở dữ liệu của ứng dụng
    private void copyDatabase() throws IOException {
        InputStream input = context.getAssets().open(DATABASE_NAME);
        String outFileName = getDatabasePath();
        OutputStream output = new FileOutputStream(outFileName);

        byte[] buffer = new byte[1024];
        int length;
        while ((length = input.read(buffer)) > 0) {
            output.write(buffer, 0, length);
        }

        output.flush();
        output.close();
        input.close();
    }

    // Mở cơ sở dữ liệu đã sao chép
    public SQLiteDatabase openDatabase() {
        String path = getDatabasePath();
        database = SQLiteDatabase.openDatabase(path, null, SQLiteDatabase.OPEN_READWRITE);
        return database;
    }

    // Đóng cơ sở dữ liệu
    public void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d("Database", "Cơ sở dữ liệu đã được đóng.");
        }
    }
    public boolean isCustomerIdExists(String maKhachHang) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM KhachHang WHERE maKhachHang = ?";
        Cursor cursor = db.rawQuery(query, new String[]{maKhachHang});
        boolean exists = (cursor.getCount() > 0);
        cursor.close(); // Đừng quên đóng con trỏ
        return exists;
    }


    // Cập nhật phương thức generateCustomerId
    public String generateCustomerId() {
        Random random = new Random();
        String maKhachHang;
        do {
            int randomNum = random.nextInt(100000); // Tạo số ngẫu nhiên từ 0 đến 99999
            maKhachHang = "KH" + randomNum; // Tạo mã khách hàng
        } while (isCustomerIdExists(maKhachHang)); // Lặp lại nếu mã khách hàng đã tồn tại
        return maKhachHang; // Trả về mã khách hàng
    }

    //thêm khách hàng mới
    public String  addCustomer(String tenKhachHang, String soDienThoai, String diaChi) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String maKhachHang = generateCustomerId(); // Tạo mã khách hàng

        values.put("maKhachHang", maKhachHang);
        values.put("tenKhachHang", tenKhachHang);
        values.put("soDienThoai", soDienThoai);
        values.put("diaChi", diaChi);

        long result = db.insert("KhachHang", null, values);
        db.close(); // Đóng cơ sở dữ liệu sau khi thực hiện

        return result != -1 ? maKhachHang : null; // Trả về mã khách hàng nếu thêm thành công, null nếu không
    }
    public boolean isAccountExists(String taiKhoan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM TaiKhoanKhachHang WHERE taiKhoan = ?";
        Cursor cursor = db.rawQuery(query, new String[]{taiKhoan});
        boolean exists = (cursor.getCount() > 0);
        cursor.close(); // Đừng quên đóng con trỏ
        return exists;
    }

    public boolean addtaikhoanuser(String tenKhachHang, String matKhau, String maKhachHang) {
        if (isAccountExists(tenKhachHang)) {
            return false; // Tài khoản đã tồn tại
        }

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("taiKhoan", tenKhachHang);
        values.put("matKhau", matKhau);
        values.put("maKhachHang", maKhachHang);

        long result = db.insert("TaiKhoanKhachHang", null, values);
        db.close(); // Đóng cơ sở dữ liệu sau khi thực hiện

        return result != -1; // Trả về true nếu thêm thành công, false nếu không
    }

    public List<SanPham> timKiemSanPham(String tuKhoa) {
        List<SanPham> ketQua = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT maSanPham, tenSanPham, maNhaCungCap, maDanhMuc, tacGia, soLuong, giaBan, maTheLoai FROM SanPham WHERE tenSanPham LIKE ?", new String[]{"%" + tuKhoa + "%"});

        if (cursor.moveToFirst()) {
            do {
                int maSanPham = cursor.getInt(0);
                String tenSanPham = cursor.getString(1);
                String maNhaCungCap = cursor.getString(2);
                String maDanhMuc = cursor.getString(3);
                String tacGia = cursor.getString(4);
                int soLuong = cursor.getInt(5);
                int giaBan = cursor.getInt(6);
                String maTheLoai = cursor.getString(7);

                ketQua.add(new SanPham(maSanPham, tenSanPham, null, maNhaCungCap, maDanhMuc, tacGia, soLuong, giaBan, maTheLoai));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return ketQua;
    }
    public String generateOrderId() {
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // Giới hạn số ngẫu nhiên (có thể điều chỉnh)
        return "DH" + randomNumber; // Kết hợp "DH" với số ngẫu nhiên
    }
    public void insertDonHang(String maDonHang, String customerId, String orderDate, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("madonhang", maDonHang);
        values.put("makhachhang", customerId);
        values.put("ngaydathang", orderDate);
        values.put("trangthaidonhang", status);

        long result = db.insert("donhang", null, values);
        if (result == -1) {
            Log.e("DonHang", "Failed to add order with ID: " + maDonHang);
        } else {
            Log.d("DonHang", "Order added successfully with ID: " + maDonHang);
        }
        db.close();
    }
    public void insertIntoChiTietDonHang(String maDonHang, int maSanPham, int soLuong) {
        SQLiteDatabase database = this.openDatabase(); // Assuming you have a method to open your database
        ContentValues values = new ContentValues();
        values.put("maDonHang", maDonHang);
        values.put("maSanPham", maSanPham);
        values.put("soLuong", soLuong);

        long result = database.insert("ChiTietDonHang", null, values);
        if (result == -1) {
            Log.e("ChiTietDonHang", "Thêm chi tiết đơn hàng thất bại cho sản phẩm: " + maSanPham);
        } else {
            Log.d("ChiTietDonHang", "Thêm chi tiết đơn hàng thành công cho sản phẩm: " + maSanPham);
        }
        database.close();
    }

    public String[] getUserByCustomerId(String customerId) {
        String[] userInfo = null; // Mảng để lưu thông tin người dùng
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "SELECT khachhang.sdt, khachhang.diachi, " +
                "taikhoankhachhang.taikhoan, taikhoankhachhang.matkhau " +
                "FROM khachhang " +
                "JOIN taikhoankhachhang ON khachhang.makhachhang = taikhoankhachhang.makhachhang " +
                "WHERE khachhang.makhachhang = ?"; // Sử dụng ? để tránh SQL Injection

        Cursor cursor = db.rawQuery(query, new String[]{customerId});
        if (cursor.moveToFirst()) {
            userInfo = new String[4]; // Chỉ cần 4 phần tử
            userInfo[0] = cursor.getString(0); // sdt
            userInfo[1] = cursor.getString(1); // diachi
            userInfo[2] = cursor.getString(2); // taikhoan
            userInfo[3] = cursor.getString(3); // matkhau
        }
        cursor.close();
        db.close();
        return userInfo; // Trả về mảng thông tin người dùng
    }
    public void updateUser(String customerId, String phone, String address, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Cập nhật bảng khachhang
        ContentValues values = new ContentValues();
        values.put("sdt", phone);
        values.put("diachi", address);
        db.update("khachhang", values, "makhachhang = ?", new String[]{customerId});

        // Cập nhật bảng taikhoankhachhang
        ContentValues accountValues = new ContentValues();
        accountValues.put("taikhoan", username);
        accountValues.put("matkhau", password);
        db.update("taikhoankhachhang", accountValues, "makhachhang = ?", new String[]{customerId});

        db.close();
    }
    public void deleteUser(String customerId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Xóa tài khoản trong bảng taikhoankhachhang
        db.delete("taikhoankhachhang", "makhachhang = ?", new String[]{customerId});

        // Xóa thông tin trong bảng khachhang
        db.delete("khachhang", "makhachhang = ?", new String[]{customerId});

        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

package com.example.appbansach;

public class SanPham {
    private int maSanPham;
    private String tenSanPham;
    private byte[] anhSanPham;
    private String nhaCungCap;
    private String danhMuc;
    private String tacGia;
    private int soLuong;
    private int giaBan;
    private String theLoai;

    public SanPham(int maSanPham, String tenSanPham, byte[] anhSanPham, String nhaCungCap, String danhMuc, String tacGia, int soLuong, int giaBan, String theLoai) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.anhSanPham = anhSanPham;
        this.nhaCungCap = nhaCungCap;
        this.danhMuc = danhMuc;
        this.tacGia = tacGia;
        this.soLuong = soLuong;
        this.giaBan = giaBan;
        this.theLoai = theLoai;
    }

    public int getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(int maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public byte[] getAnhSanPham() {
        return anhSanPham;
    }

    public void setAnhSanPham(byte[] anhSanPham) {
        this.anhSanPham = anhSanPham;
    }

    public String getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(String nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public String getDanhMuc() {
        return danhMuc;
    }

    public void setDanhMuc(String danhMuc) {
        this.danhMuc = danhMuc;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }
}
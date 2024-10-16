package com.example.appbansach;

public class SanPham {
    String maSp, tenSp, maDm, maNcc, theLoai, maTg,namXb;
    int gia, sl;

    public SanPham(String maSp, String tenSp, String maDm, String maNcc, String theLoai, String maTg, String namXb, int gia, int sl) {
        this.maSp = maSp;
        this.tenSp = tenSp;
        this.maDm = maDm;
        this.maNcc = maNcc;
        this.theLoai = theLoai;
        this.maTg = maTg;
        this.namXb = namXb;
        this.gia = gia;
        this.sl = sl;
    }

    public String getMaSp() {
        return maSp;
    }

    public void setMaSp(String maSp) {
        this.maSp = maSp;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public String getMaDm() {
        return maDm;
    }

    public void setMaDm(String maDm) {
        this.maDm = maDm;
    }

    public String getMaNcc() {
        return maNcc;
    }

    public void setMaNcc(String maNcc) {
        this.maNcc = maNcc;
    }

    public String getTheLoai() {
        return theLoai;
    }

    public void setTheLoai(String theLoai) {
        this.theLoai = theLoai;
    }

    public String getMaTg() {
        return maTg;
    }

    public void setMaTg(String maTg) {
        this.maTg = maTg;
    }

    public String getNamXb() {
        return namXb;
    }

    public void setNamXb(String namXb) {
        this.namXb = namXb;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }
}

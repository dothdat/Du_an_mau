package com.example.du_an_mau.model;

public class Sach {
    int maSach;
    String tenSach;
    String tacGia;
    int giaThue;
    int maLoai;
    int namXB;

    public Sach() {
    }

    public Sach(int maSach, String tenSach, String tacGia, int giaThue, int maLoai, int namXB) {
        this.maSach = maSach;
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
        this.namXB = namXB;
    }

    public int getMaSach() {
        return maSach;
    }

    public void setMaSach(int maSach) {
        this.maSach = maSach;
    }

    public String getTenSach() {
        return tenSach;
    }

    public void setTenSach(String tenSach) {
        this.tenSach = tenSach;
    }

    public String getTacGia() {
        return tacGia;
    }

    public void setTacGia(String tacGia) {
        this.tacGia = tacGia;
    }

    public int getGiaThue() {
        return giaThue;
    }

    public void setGiaThue(int giaThue) {
        this.giaThue = giaThue;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public int getNamXB() {
        return namXB;
    }

    public void setNamXB(int namXB) {
        this.namXB = namXB;
    }

    public Sach(String tenSach, String tacGia, int giaThue, int maLoai, int namXB) {
        this.tenSach = tenSach;
        this.tacGia = tacGia;
        this.giaThue = giaThue;
        this.maLoai = maLoai;
        this.namXB = namXB;
    }
}

package com.example.du_an_mau.model;
//maLoai integer primary key autoincrement, tenLoai text not null
public class loaiSach {
    private int maLoai;
    private String tenLoai;

    public loaiSach() {
    }

    public loaiSach(int maLoai, String tenLoai) {
        this.maLoai = maLoai;
        this.tenLoai = tenLoai;
    }

    public int getMaLoai() {
        return maLoai;
    }

    public void setMaLoai(int maLoai) {
        this.maLoai = maLoai;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public void setTenLoai(String tenLoai) {
        this.tenLoai = tenLoai;
    }
}

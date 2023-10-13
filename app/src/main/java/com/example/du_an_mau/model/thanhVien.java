package com.example.du_an_mau.model;

public class thanhVien  {
    private int maTV;
    private String tenTv;
    private String sdt;
    private String diaChi;

    public thanhVien(int maTV, String tenTv, String sdt, String diaChi) {
        this.maTV = maTV;
        this.tenTv = tenTv;
        this.sdt = sdt;
        this.diaChi = diaChi;
    }

    public thanhVien(String tentv, String sdt, String diaChi) {
    }

    public thanhVien() {
    }

    public int getMaTV() {
        return maTV;
    }

    public void setMaTV(int maTV) {
        this.maTV = maTV;
    }

    public String getTenTv() {
        return tenTv;
    }

    public void setTenTv(String tenTv) {
        this.tenTv = tenTv;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}

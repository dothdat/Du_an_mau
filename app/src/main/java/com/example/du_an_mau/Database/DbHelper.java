package com.example.du_an_mau.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {



    private static final String DATABASE_NAME="Du_an_mau_FALL_2023";
    private static final  int DATABASE_VERSION=5;
    public DbHelper( Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);}


    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng thủ thư
        String qNguoiDung = "CREATE TABLE NGUOIDUNG(" +
                "maTT text primary key, " +
                "tenTT text not null, " +
                "matKhau text not null, " +
                "chucVu integer)";
        db.execSQL(qNguoiDung);
        String dNguoiDung = "INSERT INTO NGUOIDUNG VALUES('nam','nam','123',1),('trung','trung','456',1),('dat','dat','111',0)";
        db.execSQL(dNguoiDung);
        // Bảng thành viên
        String tb_ThanhVien= "CREATE TABLE tb_ThanhVien(maTV integer primary key autoincrement, tenTV text not null, sdt text not null, diaChi text not null)";
        db.execSQL(tb_ThanhVien);
        String data_ThanhVien = "INSERT INTO tb_ThanhVien VALUES ( 1, 'Trần Ngọc Hải', '0987892122', 'Phương Canh'),( 2, 'Nguyễn Mạnh Cường', '0357893416', 'Trần Bình'),( 3, 'Ngô Xuân Bắc', '0378197311', 'Chương Mỹ')";
        db.execSQL(data_ThanhVien);
        // Bảng loai sách
        String tb_LoaiSach= "CREATE TABLE tb_LoaiSach(maLoai integer primary key autoincrement, tenLoai text not null )";
        String data_LoaiSach =  "INSERT INTO tb_LoaiSach VALUES (1, 'Truyện'), (2, 'Truyện hài'), (3, 'Thiếu nhi')";
        db.execSQL(tb_LoaiSach);
        db.execSQL(data_LoaiSach);
        // Bảng sách
        String tb_Sach= "CREATE TABLE tb_Sach(maSach integer primary key autoincrement, tenSach text not null, tacGia text not null, giaThue integer not null, maLoai integer REFERENCES tb_LoaiSach(maLoai), namXB integer not null)";
        db.execSQL(tb_Sach);
        String data_Sach= "INSERT INTO tb_Sach VALUES (1,'Dế mèn phiêu lưu ký', 'Tô Hoài', 49000, 1, 2010),(2,'Đại gia đình keo kiệt', 'Long Vũ', 29000, 2, 1998),(3,'Doraemon', 'Fujiko F. Fujio', 39000, 3, 1993)";
        db.execSQL(data_Sach);
        // Bảng Phiếu Mượn
        String tb_PhieuMuon= "CREATE TABLE tb_PhieuMuon(maPhieuMuon integer primary key autoincrement, maTV integer REFERENCES tb_ThanhVien(maTV), maSach integer REFERENCES tb_Sach(maSach),  ngayMuon text not null, tienThue integer not null , traSach integer not null)";
        db.execSQL(tb_PhieuMuon);
        String data_PhieuMuon = "INSERT INTO tb_PhieuMuon VALUES (1,1,1,'30/09/2023',30000,1),(2,2,3,'25/09/2023',60000,2),(3,3,2,'1/10/2023',45000,1)";
        db.execSQL(data_PhieuMuon);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS NGUOIDUNG");
        db.execSQL("drop table if exists tb_ThuThu");
        db.execSQL("drop table if exists tb_ThanhVien");
        db.execSQL("drop table if exists tb_LoaiSach");
        db.execSQL("drop table if exists tb_Sach");
        db.execSQL("drop table if exists tb_PhieuMuon");

        onCreate(db);

    }


}

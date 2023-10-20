package com.example.du_an_mau.Daos;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.du_an_mau.Database.DbHelper;
import com.example.du_an_mau.model.Fragment.PhieuMuon;
import com.example.du_an_mau.model.top10;

import java.util.ArrayList;

public class phieuMuon_Dao {
    private final DbHelper dbHelper;
    Sach_Dao daoSach;


    public phieuMuon_Dao(Context context) {
        dbHelper = new DbHelper(context);
        daoSach = new Sach_Dao(context);
    }
    public ArrayList<PhieuMuon> selectAll() {
        ArrayList<PhieuMuon> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        try {
            Cursor cursor =db.rawQuery("SELECT * FROM tb_PhieuMuon INNER JOIN tb_ThanhVien ON tb_PhieuMuon.maTV = tb_ThanhVien.maTV INNER JOIN tb_Sach ON tb_PhieuMuon.maSach = tb_Sach.maSach", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    PhieuMuon pm = new PhieuMuon();
                    pm.setMaPhieuMuon(cursor.getInt(0));
                    pm.setMaTV(cursor.getInt(1));
                    pm.setMaSach(cursor.getInt(2));
                    pm.setNgayMuon(cursor.getString(3));
                    pm.setTienThue(cursor.getInt(4));
                    pm.setTrangThai(cursor.getInt(5));
                    pm.setTenTV(cursor.getString(7));
                    pm.setTenSach(cursor.getString(11));
                    list.add(pm);
                    cursor.moveToNext();
                }
            }

        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }
//    public ArrayList<PhieuMuon> selectAll() {
//        ArrayList<PhieuMuon> list = new ArrayList<>();
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        SQLiteDatabase dbb = dbHelper.getWritableDatabase();
//        ContentValues values = new ContentValues();
//        try {
//            Cursor cursor =db.rawQuery("SELECT * FROM tb_PhieuMuon INNER JOIN tb_ThanhVien ON tb_PhieuMuon.maTV = tb_ThanhVien.maTV INNER JOIN tb_Sach ON tb_PhieuMuon.maSach = tb_Sach.maSach", null);
//            if (cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    PhieuMuon pm = new PhieuMuon();
//                    pm.setMaPhieuMuon(cursor.getInt(0));
//                    pm.setMaTT(cursor.getString(1));
//                    pm.setMaTV(cursor.getInt(2));
//                    pm.setMaSach(cursor.getInt(3));
//                    pm.setTienThue(cursor.getInt(12));
//                    pm.setNgayMuon(cursor.getString(5));
//                    pm.setTrangThai(cursor.getInt(6));
//                    pm.setTenTV(cursor.getString(8));
//                    pm.setTenSach(cursor.getString(11));
//                    values.put("tienThue", cursor.getInt(12));
//                    dbb.update("tb_PhieuMuon", values, "maPhieuMuon = ?", new String[]{String.valueOf(cursor.getInt(0))});
//                    list.add(pm);
//                    cursor.moveToNext();
//                }
//            }
//
//        } catch (Exception e) {
//            Log.i(TAG, "Lỗi" + e);
//        }
//        return list;
//    }
    public boolean insert(PhieuMuon pm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngayMuon", pm.getNgayMuon());
        values.put("traSach", pm.getTrangThai());
        long row = db.insert("tb_PhieuMuon", null, values);
        return (row > 0);
    }
    public boolean update(PhieuMuon pm) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("maTV", pm.getMaTV());
        values.put("maSach", pm.getMaSach());
        values.put("tienThue", pm.getTienThue());
        values.put("ngayMuon", pm.getNgayMuon());
        values.put("traSach", pm.getTrangThai());
        long row = db.update("tb_PhieuMuon", values, "maPhieuMuon = ?", new String[]{String.valueOf(pm.getMaPhieuMuon())});
        return (row > 0);
    }
    public boolean delete(int maPhieuMuon) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long row = db.delete("tb_PhieuMuon", "maPhieuMuon=?", new String[]{String.valueOf(maPhieuMuon)});
        return (row > 0);
    }
    String ngayThue;
    public String getNgayThue(String maPM) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT ngayMuon FROM tb_PhieuMuon WHERE tb_PhieuMuon.maPhieuMuon = ?", new String[] {maPM});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    ngayThue = cursor.getString(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return ngayThue;
    }

    public int getDoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<Integer> list = new ArrayList<>();
        try {
            Cursor cursor =db.rawQuery("SELECT SUM(tienThue) FROM tb_PhieuMuon WHERE ngayMuon BETWEEN ? AND ?", new String[] {tuNgay, denNgay});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    list.add(cursor.getInt(0));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list.get(0);
    }
    public ArrayList<top10> getTop() {
        ArrayList<top10> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        try {
            Cursor cursor =db.rawQuery("SELECT maSach, count(maSach) AS soLuong FROM tb_PhieuMuon GROUP BY maSach ORDER BY soLuong DESC LIMIT 10", null);
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    top10 top10 = new top10();
                    String tenS = daoSach.getTenS(cursor.getInt(0));
                    top10.setTenSach(tenS);
                    top10.setSoLuong(cursor.getInt(1));
                    list.add(top10);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return list;
    }

}

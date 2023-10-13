package com.example.du_an_mau.Daos;

import static android.service.controls.ControlsProviderService.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.du_an_mau.Database.DbHelper;
import com.example.du_an_mau.model.thanhVien;

import java.util.ArrayList;


public class Thanhvien_Dao {
    private DbHelper db_ThanhVien;

    public Thanhvien_Dao(Context context) {
        db_ThanhVien = new DbHelper(context);
    }

    public ArrayList<thanhVien> getAllND() {
        ArrayList<thanhVien> list = new ArrayList<>();
        SQLiteDatabase database = db_ThanhVien.getReadableDatabase();

        //database bat day chay
        database.beginTransaction();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM tb_ThanhVien", null);
            if (cursor.getCount() > 0) {
                //neu cursor lon hon 0 di chuyen con tro len dau
                cursor.moveToFirst();
                do {
                    list.add(new thanhVien(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3)));
                } while (cursor.moveToNext());
            }while (cursor.moveToNext()) ;
            database.setTransactionSuccessful();
        }catch (Exception e){
            Log.e("TAG",e.getMessage());
        }finally {
            database.endTransaction();
        }
        return list;
    }
    // update sản phẩm

    public boolean update(thanhVien thanhVien) {
        SQLiteDatabase database = db_ThanhVien.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("tenTV", thanhVien.getTenTv());
        values.put("sdt", thanhVien.getSdt());
        values.put("diaChi", thanhVien.getDiaChi());
        int check = database.update("tb_ThanhVien", values, "maTV = ?",
                new String[]{String.valueOf(thanhVien.getMaTV())});
        return check != 0;
    }

    //thêm sản phẩm
    public boolean insert(String tentv,String sdt,String diaChi) {
        SQLiteDatabase db = db_ThanhVien.getWritableDatabase(); //getWrite.. ghi dữ liệu vào database
        ContentValues value = new ContentValues(); // đưa dữ lệu vào database
        value.put("tenTV", tentv);
        value.put("sdt", sdt);
        value.put("diaChi", diaChi);

        long check = db.insert("tb_ThanhVien", null, value);
        return check != -1;
    }

    //xóa sản phẩm

    public boolean delete(int id) {
        SQLiteDatabase database = db_ThanhVien.getWritableDatabase();
        int check = database.delete("tb_ThanhVien", "maTV=?",
                new String[]{String.valueOf(id)});
        if (check <=0) return false;

        return true;
    }
    int row;
    public int getMaTV(String tenTV) {
        SQLiteDatabase db = db_ThanhVien.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT maTV FROM tb_ThanhVien WHERE tb_ThanhVien.tenTV = ?", new String[] {tenTV});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    row = cursor.getInt(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return row;
    }
    String tenTV;
    public String getTenTV(int maTV) {
        SQLiteDatabase db = db_ThanhVien.getReadableDatabase();
        try {
            Cursor cursor = db.rawQuery("SELECT tenTV FROM tb_ThanhVien WHERE tb_ThanhVien.maTV = ?", new String[] {String.valueOf(maTV)});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    tenTV = cursor.getString(0);
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            Log.i(TAG, "Lỗi" + e);
        }
        return tenTV;
    }
}
